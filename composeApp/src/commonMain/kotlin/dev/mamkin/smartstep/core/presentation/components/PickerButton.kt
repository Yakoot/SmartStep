package dev.mamkin.smartstep.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import org.jetbrains.compose.resources.painterResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.arrow_down

@Composable
fun PickerButton(
    label: String,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(56.dp),
        colors =  ButtonDefaults.outlinedButtonColors(
            contentColor = AppTheme.colors.textPrimary,
            containerColor = AppTheme.colors.backgroundSecondary
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        border = BorderStroke(1.dp, AppTheme.colors.strokeMain)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.colors.textSecondary
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppTheme.colors.textPrimary
                )
            }
            Icon(
                painter = painterResource(Res.drawable.arrow_down),
                contentDescription = null,
                tint = AppTheme.colors.textPrimary
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SmartStepTheme {
        PickerButton(
            label = "Label",
            text = "Text",
            onClick = {},
        )
    }
}
