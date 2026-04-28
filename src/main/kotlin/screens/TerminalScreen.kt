/*
 * SPDX-FileCopyrightText: ©2026 HOE Team
 * SPDX-License-Identifier: GPL-3.0-only
 *
 * Project: NNETB-For-Linux
 */

package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utils.TerminalSessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalScreen() {
    var commandInput by remember { mutableStateOf("") }
    var terminalOutput by remember { mutableStateOf("") }
    var isRunning by remember { mutableStateOf(false) }
    
    // Collect flow updates
    LaunchedEffect(Unit) {
        TerminalSessionManager.outputFlow.collect { output ->
            terminalOutput = output
        }
    }
    
    LaunchedEffect(Unit) {
        TerminalSessionManager.isRunning.collect { running ->
            isRunning = running
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 终端输出区域
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.9f)
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                val scrollState = rememberScrollState()
                
                LaunchedEffect(terminalOutput) {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
                
                Text(
                    text = terminalOutput,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .verticalScroll(scrollState),
                    style = TextStyle(
                        color = Color.Green,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    maxLines = Int.MAX_VALUE
                )
            }
        }
        
        // 控制按钮行
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 清空按钮
            OutlinedButton(
                onClick = { TerminalSessionManager.clearOutput() },
                modifier = Modifier.weight(1f),
                enabled = terminalOutput.isNotEmpty()
            ) {
                Icon(Icons.Default.Clear, contentDescription = "清空", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("清空输出")
            }
            
            // 停止按钮
            OutlinedButton(
                onClick = { TerminalSessionManager.stopCurrentProcess() },
                modifier = Modifier.weight(1f),
                enabled = isRunning,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Default.Stop, contentDescription = "停止", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("停止进程")
            }
        }
        
        // 指令输入区域 - 遵循Material Design规范
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 命令输入框 - 使用OutlinedTextField遵循Material Design
            OutlinedTextField(
                value = commandInput,
                onValueChange = { commandInput = it },
                modifier = Modifier.weight(1f),
                label = { Text("指令输入") },
                placeholder = { Text("按下回车或发送键发送") },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (commandInput.isNotBlank()) {
                            TerminalSessionManager.executeCommand(commandInput)
                            commandInput = ""
                        }
                    }
                )
            )
            
            // 发送按钮
            Button(
                onClick = {
                    if (commandInput.isNotBlank()) {
                        TerminalSessionManager.executeCommand(commandInput)
                        commandInput = ""
                    }
                },
                modifier = Modifier.height(56.dp),
                enabled = commandInput.isNotBlank() && !isRunning
            ) {
                Icon(Icons.Default.Send, contentDescription = "发送", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("发送")
            }
        }
    }
}