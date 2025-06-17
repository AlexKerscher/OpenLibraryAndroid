package dev.kerscher.openlibraryandroid

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TestAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1E3A5F),
            secondary = Color(0xFF1565C0),
            tertiary = Color(0xFFCCCCCC),
        )
    ) {
        content()
    }
}