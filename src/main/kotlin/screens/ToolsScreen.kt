package screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.Desktop
import java.net.URI
import utils.PackageManagerUtils
import utils.PackageManagerType
import utils.CommonPackages
import utils.TerminalSessionManager

data class ToolItem(
    val name: String,
    val description: String,
    val url: String,
    val isProprietarySoftware: Boolean = false,
    val licenseUrl: String? = null,
    val eulaUrl: String? = null,
    val licenseType: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        "系统信息与硬件监控工具",
        "驱动程序管理工具",
        "媒体工具",
        "文件工具",
        "开发人员工具",
        "其它工具"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // Tabs
        TabRow(selectedTabIndex = selectedTab, modifier = Modifier.fillMaxWidth()) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontSize = 12.sp) }
                )
            }
        }

        // Tab content
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            when (selectedTab) {
                0 -> SystemMonitoringToolContent()
                1 -> DriverManagementToolContent()
                2 -> MediaToolContent()
                3 -> FileToolContent()
                4 -> DeveloperToolContent()
                5 -> OtherToolContent()
            }
        }
    }
}

@Composable
fun SystemMonitoringToolContent() {
    val tools = listOf(
        ToolItem("HWiNFO", "硬件信息实时监控", "https://www.hwinfo.com/", 
            isProprietarySoftware = true),
        ToolItem("lshw", "硬件信息检测工具", "https://ezix.org/project/wiki/HardwareLiSter",
            licenseUrl = "https://ezix.org/project/wiki/HardwareLiSter#License",
            licenseType = "GPL-2.0"),
        ToolItem("dmidecode", "DMI表信息读取工具", "https://www.nongnu.org/dmidecode/",
            licenseUrl = "https://www.nongnu.org/dmidecode/",
            licenseType = "GPL-2.0")
    )
    ToolCardGrid(tools)
}

@Composable
fun DriverManagementToolContent() {
    val tools = listOf(
        ToolItem("nvidia-driver", "NVIDIA显卡驱动", "https://www.nvidia.com/Download/index.aspx",
            isProprietarySoftware = true),
        ToolItem("mesa-vulkan-drivers", "开源显卡驱动", "https://www.mesa3d.org/",
            licenseUrl = "https://www.mesa3d.org/license.html",
            licenseType = "MIT")
    )
    ToolCardGrid(tools)
}

@Composable
fun MediaToolContent() {
    val tools = listOf(
        ToolItem("VLC", "多媒体播放器", "https://www.videolan.org/vlc/", 
            licenseUrl = "https://www.videolan.org/legal.html",
            licenseType = "GPL-2.0"),
        ToolItem("SMPlayer","多功能播放器", "https://github.com/smplayer-dev/smplayer/releases/latest",
            licenseUrl = "https://github.com/smplayer-dev/smplayer/blob/master/Copying.txt",
            licenseType = "GPL-2.0"),
        ToolItem("Kodi","家庭影院中心", "https://kodi.tv/",
            licenseUrl = "https://github.com/xbmc/xbmc/blob/master/LICENSES/GPL-2.0-or-later",
            licenseType = "GPL-2.0"),
        ToolItem("OpenShot","开源剪辑软件", "https://www.openshot.org/zh-hans/",
            licenseUrl = "https://github.com/OpenShot/openshot-qt/blob/develop/COPYING",
            licenseType = "GPL-3.0"),
        ToolItem("kdenlive","KDE 非线性视频编辑器", "https://kdenlive.org/zh-cn/",
            licenseUrl = "https://invent.kde.org/multimedia/kdenlive/-/blob/master/COPYING",
            licenseType = "GPL-3.0"),
        ToolItem("OBS Studio","专业录制软件", "https://obsproject.com/zh-cn/download",
            licenseUrl = "https://github.com/obsproject/obs-studio/blob/master/COPYING",
            licenseType = "GPL-2.0"),
    )
    ToolCardGrid(tools)
}

@Composable
fun FileToolContent() {
    val tools = listOf(
        ToolItem("rsync", "文件同步工具", "https://rsync.samba.org/",
            licenseUrl = "https://rsync.samba.org/",
            licenseType = "GPL-3.0"),
        ToolItem("FileLight", "磁盘占用查看器", "https://apps.kde.org/zh-cn/filelight/",
            licenseUrl = "https://invent.kde.org/utilities/filelight/-/tree/master/LICENSES?ref_type=heads",
            licenseType = "GPL-3.0")
    )
    ToolCardGrid(tools)
}

@Composable
fun DeveloperToolContent() {
    val tools = listOf(
        ToolItem("Git", "版本控制工具", "https://git-scm.com/",
            licenseUrl = "https://github.com/git/git/blob/master/COPYING",
            licenseType = "GPL-2.0"),
        ToolItem("vim", "文本编辑器", "https://www.vim.org/",
            licenseUrl = "https://github.com/vim/vim/blob/master/LICENSE",
            licenseType = "Vim"),
        ToolItem("vscode", "代码编辑器", "https://code.visualstudio.com/",
            isProprietarySoftware = true,
            eulaUrl = "https://code.visualstudio.com/license")
    )
    ToolCardGrid(tools)
}

@Composable
fun OtherToolContent() {
    val tools = listOf(
        ToolItem("woeusb", "USB镜像烧录工具", "https://github.com/slacka/WoeUSB",
            licenseUrl = "https://github.com/slacka/WoeUSB/blob/master/COPYING",
            licenseType = "GPL-3.0"),
    )
    ToolCardGrid(tools)
}

@Composable
fun ToolCardGrid(tools: List<ToolItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Group tools into pairs (2 per row)
        tools.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                row.forEach { tool ->
                    ToolCard(
                        tool = tool,
                        modifier = Modifier.weight(1f)
                    )
                }
                // If odd number, add spacer to balance
                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ToolCard(tool: ToolItem, modifier: Modifier = Modifier) {
    // 检测当前包管理器
    val packageManager by remember { mutableStateOf(PackageManagerUtils.detectPackageManager()) }
    // 获取包信息
    val packageInfo = remember(tool.name) { CommonPackages.getPackageInfo(tool.name) }
    // 获取对应包管理器的包名
    val packageName = remember(packageInfo, packageManager) {
        packageInfo?.getPackageNameForManager(packageManager)
    }
    
    Card(
        modifier = modifier
            .height(200.dp), // 增加高度以容纳包名信息
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = tool.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tool.description,
                style = MaterialTheme.typography.bodySmall
            )
            
            // 显示Linux包名（如果可用）
            if (packageName != null && packageManager != PackageManagerType.UNKNOWN) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = "Linux包名",
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Linux包名: $packageName",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // 安装按钮（专有软件和开源软件都使用安装按钮）
            val installCommand = remember(packageName, packageManager) {
                if (packageName != null && packageManager != PackageManagerType.UNKNOWN) {
                    PackageManagerUtils.getInstallCommand(packageManager, packageName)
                } else {
                    null
                }
            }
            
            // 显示安装按钮或备用按钮
            if (installCommand != null) {
                // 有包名和包管理器，显示安装按钮
                var showInstallDialog by remember { mutableStateOf(false) }
                
                Button(
                    onClick = { showInstallDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = packageManager != PackageManagerType.UNKNOWN
                ) {
                    Icon(Icons.Default.Download, contentDescription = "安装", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("安装", fontSize = 12.sp)
                }
                
                // 安装确认对话框
                if (showInstallDialog) {
                    InstallConfirmationDialog(
                        toolName = tool.name,
                        installCommand = installCommand,
                        onDismiss = { showInstallDialog = false },
                        onConfirm = {
                            // 使用TerminalSessionManager执行命令
                            TerminalSessionManager.executeCommand(installCommand)
                            showInstallDialog = false
                        }
                    )
                }
            } else {
                // 没有包名或包管理器，显示备用按钮
                if (tool.isProprietarySoftware) {
                    // 专有软件：显示警告和查看条款按钮
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "警告",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "专有软件，请遵守其条款",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    
                    if (tool.eulaUrl != null) {
                        OutlinedButton(
                            onClick = { openToolWebsite(tool.eulaUrl) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Icon(Icons.Default.Description, contentDescription = "查看条款", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("查看条款", fontSize = 12.sp)
                        }
                    }
                } else {
                    // 开源软件：显示查看许可证按钮
                    val licenseUrl = tool.licenseUrl ?: getDefaultLicenseUrl(tool.url)
                    if (licenseUrl != null) {
                        OutlinedButton(
                            onClick = { openToolWebsite(licenseUrl) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Icon(Icons.Default.Description, contentDescription = "查看许可证", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("查看许可证", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

private fun getDefaultLicenseUrl(toolUrl: String): String? {
    // 尝试从工具URL推断许可证URL
    return when {
        toolUrl.contains("github.com") -> {
            // GitHub仓库，添加/LICENSE路径
            toolUrl.removeSuffix("/") + "/blob/main/LICENSE"
        }
        toolUrl.contains("gitlab.com") -> {
            // GitLab仓库
            toolUrl.removeSuffix("/") + "/-/blob/main/LICENSE"
        }
        else -> null
    }
}

private fun openToolWebsite(url: String) {
    try {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(url))
            println("Opened website: $url")
        } else {
            println("Desktop browsing is not supported on this platform")
        }
    } catch (e: Exception) {
        println("Failed to open website $url: ${e.message}")
        e.printStackTrace()
    }
}

/**
 * 安装确认对话框
 */
@Composable
fun InstallConfirmationDialog(
    toolName: String,
    installCommand: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var showOutputDialog by remember { mutableStateOf(false) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("确认安装") },
        text = { 
            Column {
                Text("将执行以下命令安装 $toolName:")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = installCommand,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "注意：这需要管理员权限，可能会要求输入密码。",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    showOutputDialog = true
                }
            ) {
                Text("确认安装")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss
            ) {
                Text("取消")
            }
        }
    )
    
    // 输出对话框
    if (showOutputDialog) {
        OutputDialog(
            onDismiss = {
                showOutputDialog = false
                onDismiss()
            }
        )
    }
}

/**
 * 输出对话框，显示终端实时输出
 */
@Composable
fun OutputDialog(
    onDismiss: () -> Unit
) {
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
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("输出") },
        text = { 
            Column {
                // 输出显示区域
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(
                            color = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.9f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp)
                ) {
                    val scrollState = rememberScrollState()
                    
                    LaunchedEffect(terminalOutput) {
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }
                    
                    Text(
                        text = terminalOutput,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        style = androidx.compose.ui.text.TextStyle(
                            color = androidx.compose.ui.graphics.Color.Green,
                            fontSize = 10.sp,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        ),
                        maxLines = Int.MAX_VALUE
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // 状态提示
                if (isRunning) {
                    Text(
                        text = "安装正在进行中...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = "安装已完成。",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        confirmButton = {
            if (isRunning) {
                // 如果进程正在运行，显示"取消"按钮（发送Ctrl+C）
                OutlinedButton(
                    onClick = {
                        TerminalSessionManager.stopCurrentProcess()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Close, contentDescription = "取消", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("取消 (Ctrl+C)")
                }
            } else {
                // 如果进程已完成，显示"关闭"按钮
                Button(
                    onClick = onDismiss
                ) {
                    Text("关闭")
                }
            }
        }
    )
}
