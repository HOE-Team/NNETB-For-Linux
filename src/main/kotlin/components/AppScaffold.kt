package components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    startBar: @Composable () -> Unit = {},
    topBarTitle: String = "概览",
    content: @Composable () -> Unit = {}
) {
    // 检测当前操作系统
    val isLinux = System.getProperty("os.name").lowercase().contains("linux")
    
    MaterialTheme {
        Scaffold(
        ) { paddingValues ->
            Row(modifier = Modifier.padding(paddingValues)) {
                // Left rail occupies full height
                Box(modifier = Modifier.fillMaxHeight()) {
                    startBar()
                }

                // Right side: TopBar at top, then content fills remaining space
                Column(modifier = Modifier.fillMaxSize()) {
                    TopBar(title = topBarTitle)
                    
                    // 如果不是Linux，显示警告
                    if (!isLinux) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "警告",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = "当前系统非Linux环境，您将无法使用大部分功能",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    
                    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                        content()
                    }
                }
            }
        }
    }
}
