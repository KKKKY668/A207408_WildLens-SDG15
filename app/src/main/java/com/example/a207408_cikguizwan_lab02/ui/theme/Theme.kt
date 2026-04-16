package com.example.a207408_cikguizwan_lab02.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FreshColorScheme = lightColorScheme(
    primary = MintGreenPrimary,
    secondary = MintGreenSecondary,
    tertiary = MintGreenTertiary,
    background = FreshBg,
    surface = FreshSurface,
    onPrimary = Color.White,
    onBackground = FreshOnSurface,
    onSurface = FreshOnSurface
)

@Composable
fun WildLensTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FreshColorScheme, // 强制使用我们定义的清新配色
        typography = Typography,
        content = content
    )
}