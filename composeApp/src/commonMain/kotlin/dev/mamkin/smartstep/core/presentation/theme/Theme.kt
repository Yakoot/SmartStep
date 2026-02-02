package dev.mamkin.smartstep.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Outline

@Composable
fun SmartStepTheme(
    content: @Composable () -> Unit
) {
    val colors = LightAppColors
    CompositionLocalProvider(LocalAppColors provides colors) {
        MaterialTheme(
            typography = VibePlayerTypography,
            colorScheme = colors.material,
            content = content
        )
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}