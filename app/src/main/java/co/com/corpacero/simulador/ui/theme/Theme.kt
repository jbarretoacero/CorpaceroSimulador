package co.com.corpacero.simulador.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary            = CorpBlue,
    onPrimary          = Color.White,
    primaryContainer   = CorpBlueSoft,
    onPrimaryContainer = CorpBlueDeep,
    secondary          = CorpCyan,
    onSecondary        = Color.White,
    secondaryContainer = Color(0xFFCFEDFF),
    onSecondaryContainer = CorpBlueDeep,
    tertiary           = CorpViolet,
    onTertiary         = Color.White,
    background         = CorpGreyBg,
    onBackground       = CorpTextDark,
    surface            = CorpGreyCard,
    onSurface          = CorpTextDark,
    surfaceVariant     = CorpBlueSoft,
    onSurfaceVariant   = CorpBlueDeep,
    outline            = CorpDivider,
    outlineVariant     = CorpDivider,
    error              = Color(0xFFB3261E),
    onError            = Color.White,
)

@Composable
fun CorpaceroSimuladorTheme(
    darkTheme: Boolean = false,                  // Forzamos modo claro institucional
    @Suppress("UNUSED_PARAMETER") dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = CorpBlue.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CorpTypography,
        content = content
    )
}
