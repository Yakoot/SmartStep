package dev.mamkin.smartstep.core.presentation.components.layouts


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.components.AdaptiveOverlay
import dev.mamkin.smartstep.core.presentation.components.BaseSkeleton
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.enable_access_manually
import smartstep.composeapp.generated.resources.enable_access_manually_first_step
import smartstep.composeapp.generated.resources.enable_access_manually_message
import smartstep.composeapp.generated.resources.enable_access_manually_second_step
import smartstep.composeapp.generated.resources.enable_access_manually_third_step
import smartstep.composeapp.generated.resources.open_settings

@Composable
fun ManualPermissionLayout(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    AdaptiveOverlay(onDismiss = onDismiss) {
        BaseSkeleton(
            title = {
                Text(
                    text = stringResource(Res.string.enable_access_manually),
                    color = AppTheme.colors.textPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            },
            body = {
                Column {
                    Text(
                        text = stringResource(Res.string.enable_access_manually_message),
                        color = AppTheme.colors.textSecondary,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(Res.string.enable_access_manually_first_step),
                        color = AppTheme.colors.textPrimary,
                        style = MaterialTheme.typography.bodyLargeMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 24.dp)
                    )

                    Text(
                        text = stringResource(Res.string.enable_access_manually_second_step),
                        color = AppTheme.colors.textPrimary,
                        style = MaterialTheme.typography.bodyLargeMedium,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(Res.string.enable_access_manually_third_step),
                        color = AppTheme.colors.textPrimary,
                        style = MaterialTheme.typography.bodyLargeMedium,
                        textAlign = TextAlign.Center
                    )

                }

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
                    Text(stringResource(Res.string.open_settings))
                }
            }, modifier = modifier.padding(horizontal = 16.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun ManualPermissionLayoutPreview(modifier: Modifier = Modifier) {
    SmartStepTheme {
        ManualPermissionLayout(onDismiss = {})
    }
}