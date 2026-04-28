/*
 * SPDX-FileCopyrightText: ©2026 HOE Team
 * SPDX-License-Identifier: GPL-3.0-only
 *
 * Project: NNETB-For-Linux
 */

package utils

import androidx.compose.runtime.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * 终端会话管理器，用于管理终端会话和命令执行
 */
object TerminalSessionManager {
    private var currentProcess: Process? = null
    private val outputBuffer = ConcurrentLinkedQueue<String>()
    private val _outputFlow = MutableStateFlow("")
    val outputFlow: StateFlow<String> = _outputFlow.asStateFlow()
    
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()
    
    private var outputReaderJob: Job? = null
    private var errorReaderJob: Job? = null
    
    /**
     * 执行命令并返回进程
     */
    fun executeCommand(command: String): Process? {
        return try {
            // 如果已有进程在运行，先停止它
            stopCurrentProcess()
            
            // 添加命令到输出
            addOutput("$ $command\n")
            
            // 创建新进程
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", command))
            currentProcess = process
            _isRunning.value = true
            
            // 启动输出读取线程
            outputReaderJob = CoroutineScope(Dispatchers.IO).launch {
                val reader = process.inputStream.bufferedReader()
                try {
                    var line = reader.readLine()
                    while (isActive && line != null) {
                        addOutput("$line\n")
                        line = reader.readLine()
                    }
                } catch (e: IOException) {
                    if (isActive) {
                        addOutput("输出读取错误: ${e.message}\n")
                    }
                }
            }
            
            // 启动错误读取线程
            errorReaderJob = CoroutineScope(Dispatchers.IO).launch {
                val reader = process.errorStream.bufferedReader()
                try {
                    var line = reader.readLine()
                    while (isActive && line != null) {
                        addOutput("错误: $line\n")
                        line = reader.readLine()
                    }
                } catch (e: IOException) {
                    if (isActive) {
                        addOutput("错误读取错误: ${e.message}\n")
                    }
                }
            }
            
            // 等待进程完成
            CoroutineScope(Dispatchers.IO).launch {
                val exitCode = process.waitFor()
                outputReaderJob?.join()
                errorReaderJob?.join()
                
                addOutput("\n进程退出，退出码: $exitCode\n")
                _isRunning.value = false
                currentProcess = null
            }
            
            process
        } catch (e: Exception) {
            addOutput("执行命令时出错: ${e.message}\n")
            _isRunning.value = false
            null
        }
    }
    
    /**
     * 停止当前进程
     */
    fun stopCurrentProcess() {
        currentProcess?.let { process ->
            try {
                process.destroy()
                if (process.isAlive) {
                    process.destroyForcibly()
                }
                addOutput("\n进程已停止 (Ctrl+C)\n")
            } catch (e: Exception) {
                addOutput("停止进程时出错: ${e.message}\n")
            }
        }
        
        outputReaderJob?.cancel()
        errorReaderJob?.cancel()
        currentProcess = null
        _isRunning.value = false
    }
    
    /**
     * 添加输出到缓冲区
     */
    private fun addOutput(text: String) {
        outputBuffer.add(text)
        if (outputBuffer.size > 1000) { // 限制缓冲区大小
            outputBuffer.poll()
        }
        _outputFlow.value = outputBuffer.joinToString("")
    }
    
    /**
     * 清空输出
     */
    fun clearOutput() {
        outputBuffer.clear()
        _outputFlow.value = ""
    }
    
    /**
     * 获取当前输出
     */
    fun getOutput(): String = outputBuffer.joinToString("")
    
    /**
     * 获取最后N行输出
     */
    fun getLastLines(n: Int): String {
        val lines = outputBuffer.joinToString("").lines()
        return lines.takeLast(n).joinToString("\n")
    }
}