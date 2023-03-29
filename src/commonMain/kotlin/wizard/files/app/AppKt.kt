package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

class AppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/commonMain/kotlin/${info.packagePath}/App.kt"
    override val content = """
        package ${info.packageId}
        
        import androidx.compose.foundation.layout.Box
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.material.Button
        import androidx.compose.material.Text
        import androidx.compose.runtime.Composable
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        
        @Composable
        internal fun App() = AppTheme {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { openUrl("https://github.com/terrakok") }
                ) {
                    Text("Open github")
                }
            }
        }
        
        internal expect fun openUrl(url: String?)
    """.trimIndent()
}

class AndroidAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/androidMain/kotlin/${info.packagePath}/App.android.kt"
    override val content = """
        package ${info.packageId}

        import android.app.Application
        import android.content.Intent
        import android.net.Uri
        import android.os.Bundle
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
        
        class AndroidApp : Application() {
            companion object {
                lateinit var INSTANCE: AndroidApp
            }

            override fun onCreate() {
                super.onCreate()
                INSTANCE = this
            }
        }
        
        class AppActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContent { App() }
            }
        }
        
        internal actual fun openUrl(url: String?) {
            val uri = url?.let { Uri.parse(it) } ?: return
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = uri
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            AndroidApp.INSTANCE.startActivity(intent)
        }
    """.trimIndent()
}

class DesktopAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/desktopMain/kotlin/${info.packagePath}/App.jvm.kt"
    override val content = """
        package ${info.packageId}

        import java.awt.Desktop
        import java.net.URI

        internal actual fun openUrl(url: String?) {
            val uri = url?.let { URI.create(it) } ?: return
            Desktop.getDesktop().browse(uri)
        }
    """.trimIndent()
}

class IosAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/iosMain/kotlin/${info.packagePath}/App.ios.kt"
    override val content = """
        package ${info.packageId}

        import platform.Foundation.NSURL
        import platform.UIKit.UIApplication

        internal actual fun openUrl(url: String?) {
            val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    """.trimIndent()
}

class BrowserAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/jsMain/kotlin/${info.packagePath}/App.js.kt"
    override val content = """
        package ${info.packageId}

        import kotlinx.browser.window

        internal actual fun openUrl(url: String?) {
            url?.let { window.open(it) }
        }
    """.trimIndent()
}