package com.example.myapplication.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.material.Typography
private val LightColorPalette = lightColors(
    primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFF3700B3),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5)
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography, // ✅ Typography đã có sẵn trong Compose
        shapes = Shapes,         // ✅ Đảm bảo Shapes đã được khai báo
        content = content
    )
}
