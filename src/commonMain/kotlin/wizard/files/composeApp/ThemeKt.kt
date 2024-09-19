package wizard.files.composeApp

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

class ThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/kotlin/${info.packagePath}/theme/Theme.kt"
    override val content = """
        package ${info.packageId}.theme

        import androidx.compose.foundation.isSystemInDarkTheme
        import androidx.compose.material3.MaterialTheme
        import androidx.compose.material3.Surface
        import androidx.compose.material3.darkColorScheme
        import androidx.compose.material3.lightColorScheme
        import androidx.compose.runtime.*

        private val LightColorScheme = lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            primaryContainer = md_theme_light_primaryContainer,
            onPrimaryContainer = md_theme_light_onPrimaryContainer,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            secondaryContainer = md_theme_light_secondaryContainer,
            onSecondaryContainer = md_theme_light_onSecondaryContainer,
            tertiary = md_theme_light_tertiary,
            onTertiary = md_theme_light_onTertiary,
            tertiaryContainer = md_theme_light_tertiaryContainer,
            onTertiaryContainer = md_theme_light_onTertiaryContainer,
            error = md_theme_light_error,
            errorContainer = md_theme_light_errorContainer,
            onError = md_theme_light_onError,
            onErrorContainer = md_theme_light_onErrorContainer,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
            surfaceVariant = md_theme_light_surfaceVariant,
            onSurfaceVariant = md_theme_light_onSurfaceVariant,
            outline = md_theme_light_outline,
            inverseOnSurface = md_theme_light_inverseOnSurface,
            inverseSurface = md_theme_light_inverseSurface,
            inversePrimary = md_theme_light_inversePrimary,
            surfaceTint = md_theme_light_surfaceTint,
            outlineVariant = md_theme_light_outlineVariant,
            scrim = md_theme_light_scrim,
        )

        private val DarkColorScheme = darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            primaryContainer = md_theme_dark_primaryContainer,
            onPrimaryContainer = md_theme_dark_onPrimaryContainer,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            secondaryContainer = md_theme_dark_secondaryContainer,
            onSecondaryContainer = md_theme_dark_onSecondaryContainer,
            tertiary = md_theme_dark_tertiary,
            onTertiary = md_theme_dark_onTertiary,
            tertiaryContainer = md_theme_dark_tertiaryContainer,
            onTertiaryContainer = md_theme_dark_onTertiaryContainer,
            error = md_theme_dark_error,
            errorContainer = md_theme_dark_errorContainer,
            onError = md_theme_dark_onError,
            onErrorContainer = md_theme_dark_onErrorContainer,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
            surfaceVariant = md_theme_dark_surfaceVariant,
            onSurfaceVariant = md_theme_dark_onSurfaceVariant,
            outline = md_theme_dark_outline,
            inverseOnSurface = md_theme_dark_inverseOnSurface,
            inverseSurface = md_theme_dark_inverseSurface,
            inversePrimary = md_theme_dark_inversePrimary,
            surfaceTint = md_theme_dark_surfaceTint,
            outlineVariant = md_theme_dark_outlineVariant,
            scrim = md_theme_dark_scrim,
        )

        internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

        @Composable
        internal fun AppTheme(
            content: @Composable () -> Unit
        ) {
            val systemIsDark = isSystemInDarkTheme()
            val isDarkState = remember(systemIsDark) { mutableStateOf(systemIsDark) }
            CompositionLocalProvider(
                LocalThemeIsDark provides isDarkState
            ) {
                val isDark by isDarkState
                SystemAppearance(!isDark)
                MaterialTheme(
                    colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
                    content = { Surface(content = content) }
                )
            }
        }

        @Composable
        internal expect fun SystemAppearance(isDark: Boolean)

    """.trimIndent()
}

class DesktopThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/jvmMain/kotlin/${info.packagePath}/theme/Theme.jvm.kt"
    override val content = """
        package ${info.packageId}.theme

        import androidx.compose.runtime.Composable

        @Composable
        internal actual fun SystemAppearance(isDark: Boolean) {
        }
    """.trimIndent()
}

class JsThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/jsMain/kotlin/${info.packagePath}/theme/Theme.js.kt"
    override val content = getBrowserThemeKt(info)
}

class WasmJsThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/wasmJsMain/kotlin/${info.packagePath}/theme/Theme.wasmJs.kt"
    override val content = getBrowserThemeKt(info)
}

private fun getBrowserThemeKt(info: ProjectInfo) = """
    package ${info.packageId}.theme

    import androidx.compose.runtime.Composable

    @Composable
    internal actual fun SystemAppearance(isDark: Boolean) {
    }
""".trimIndent()

class IosThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/iosMain/kotlin/${info.packagePath}/theme/Theme.ios.kt"
    override val content = """
        package ${info.packageId}.theme

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.LaunchedEffect
        import platform.UIKit.UIApplication
        import platform.UIKit.UIStatusBarStyleDarkContent
        import platform.UIKit.UIStatusBarStyleLightContent
        import platform.UIKit.setStatusBarStyle

        @Composable
        internal actual fun SystemAppearance(isDark: Boolean) {
            LaunchedEffect(isDark) {
                UIApplication.sharedApplication.setStatusBarStyle(
                    if (isDark) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent
                )
            }
        }
    """.trimIndent()
}

class AndroidThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/androidMain/kotlin/${info.packagePath}/theme/Theme.android.kt"
    override val content = """
        package ${info.packageId}.theme

        import android.app.Activity
        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.LaunchedEffect
        import androidx.compose.ui.platform.LocalView
        import androidx.core.view.WindowInsetsControllerCompat

        @Composable
        internal actual fun SystemAppearance(isDark: Boolean) {
            val view = LocalView.current
            LaunchedEffect(isDark) {
                val window = (view.context as Activity).window
                WindowInsetsControllerCompat(window, window.decorView).apply {
                    isAppearanceLightStatusBars = isDark
                    isAppearanceLightNavigationBars = isDark
                }
            }
        }
    """.trimIndent()
}