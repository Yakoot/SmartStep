package dev.mamkin.smartstep.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    type: AppButtonType,
    modifier: Modifier = Modifier,
) {
    val border = when (type) {
        AppButtonType.FILLED -> null
        AppButtonType.OUTLINED -> BorderStroke(1.dp, AppTheme.colors.strokeMain)
        AppButtonType.TEXT -> null
    }
    val colors = when (type) {
        AppButtonType.FILLED -> ButtonDefaults.buttonColors(
            contentColor = AppTheme.colors.textWhite,
            containerColor = AppTheme.colors.buttonPrimary
        )
        AppButtonType.OUTLINED -> ButtonDefaults.outlinedButtonColors(
            contentColor = AppTheme.colors.textPrimary,
            containerColor = Color.Transparent
        )
        AppButtonType.TEXT -> ButtonDefaults.textButtonColors(
            contentColor = AppTheme.colors.buttonPrimary,
            containerColor = Color.Transparent
        )
    }

    Button(
        modifier = modifier.height(44.dp),
        onClick = onClick,
        border = border,
        shape = RoundedCornerShape(10.dp),
        colors = colors
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLargeMedium
        )

    }
}

enum class AppButtonType {
    FILLED, OUTLINED, TEXT
}

@Preview
@Composable
private fun Preview() {
    SmartStepTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppButton(text = "Button", type = AppButtonType.FILLED, onClick = {})
            AppButton(text = "Button", type = AppButtonType.OUTLINED, onClick = {})
            AppButton(text = "Button", type = AppButtonType.TEXT, onClick = {})
        }
    }
}
