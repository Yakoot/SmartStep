package dev.mamkin.smartstep.core.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val RoyalBlue = Color(0xFF3A43B6)
val Lavender = Color(0xFFE4E6FC)
val Charcoal = Color(0xFF1F2024)
val SlateGray = Color(0xFF6C6E71)
val White = Color(0xFFFFFFFF)
val SkyBlue = Color(0xFFE4F0FB)
val GhostWhite = Color(0xFFF9FAFB)
val Platinum = Color(0xFFF0F0F0)
val Silver = Color(0xFFE5E7EB)

data class AppColors(
    val buttonPrimary: Color,
    val buttonSecondary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textWhite: Color,
    val textWhite90: Color,
    val backgroundMain: Color,
    val backgroundSecondary: Color,
    val backgroundTertiary: Color,
    val backgroundWhite: Color,
    val backgroundWhite20: Color,
    val strokeMain: Color,
    val material: ColorScheme
)

val LightAppColors = AppColors(
    buttonPrimary = RoyalBlue,
    buttonSecondary = Lavender,
    textPrimary = Charcoal,
    textSecondary = SlateGray,
    textWhite = White,
    textWhite90 = White.copy(alpha = 0.9f),
    backgroundMain = SkyBlue,
    backgroundSecondary = GhostWhite,
    backgroundTertiary = Platinum,
    backgroundWhite = White,
    backgroundWhite20 = White.copy(alpha = 0.2f),
    strokeMain = Silver,
    material = lightColorScheme(
        primary = RoyalBlue,
        onPrimary = White,
        surface = Platinum,
        onSurface = Charcoal
    )
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }