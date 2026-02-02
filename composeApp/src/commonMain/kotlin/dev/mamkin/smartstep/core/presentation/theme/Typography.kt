package dev.mamkin.smartstep.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.inter
import smartstep.composeapp.generated.resources.inter_medium
import smartstep.composeapp.generated.resources.inter_semibold


val InterFontFamily
    @Composable get() = FontFamily(
        Font(Res.font.inter, weight = FontWeight.Normal),
        Font(Res.font.inter_semibold, weight = FontWeight.SemiBold),
        Font(Res.font.inter_medium, weight = FontWeight.Medium),
    )

val baseline = Typography()

val VibePlayerTypography
    @Composable get() = Typography(
        titleMedium = baseline.titleMedium.copy(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ),

        bodyLarge = baseline.bodyLarge.copy(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodyMedium = baseline.bodyMedium.copy(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp
        ),
        bodySmall = baseline.bodySmall.copy(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    )

val Typography.bodyLargeMedium
    get() = bodyLarge.copy(fontWeight = FontWeight.Medium)

val Typography.bodyMediumMedium
    get() = bodyMedium.copy(fontWeight = FontWeight.Medium)

val Typography.titleAccent
    @Composable get() = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 64.sp,
        lineHeight = 70.sp,
    )

