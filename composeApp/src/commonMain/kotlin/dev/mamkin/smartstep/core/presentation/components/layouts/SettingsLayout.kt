package dev.mamkin.smartstep.core.presentation.components.layouts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.components.AdaptiveOverlay
import dev.mamkin.smartstep.core.presentation.components.BaseSkeleton
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.app_will_fully_close
import smartstep.composeapp.generated.resources.ok
import smartstep.composeapp.generated.resources.shutdown

@Composable
fun SettingsLayout(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    AdaptiveOverlay(onDismiss = onDismiss) {
        BaseSkeleton(
            title = {
                Icon(
                    painter = painterResource(Res.drawable.shutdown),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            body = {
                Text(
                    text = stringResource(Res.string.app_will_fully_close),
                    color = AppTheme.colors.textSecondary,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            footer = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.buttonPrimary,
                        contentColor = AppTheme.colors.textWhite
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(Res.string.ok))
                }
            }, modifier = modifier.padding(horizontal = 16.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun SettingsLayoutPreview(modifier: Modifier = Modifier) {
    SmartStepTheme {
        SettingsLayout(onDismiss = {})
    }
}