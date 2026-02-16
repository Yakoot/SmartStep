package dev.mamkin.smartstep.core.presentation.components.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.components.AdaptiveOverlay
import dev.mamkin.smartstep.core.presentation.components.AppButton
import dev.mamkin.smartstep.core.presentation.components.AppButtonType
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualPermissionLayout(
    sheetState: SheetState,
    onOpenSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AdaptiveOverlay(
        sheetState = sheetState,
        onDismiss = { },
        dialogProperties = ModalBottomSheetProperties(
            shouldDismissOnClickOutside = false,
            shouldDismissOnBackPress = false,
        ),
    ) {
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
                AppButton(
                    text = stringResource(Res.string.open_settings),
                    type = AppButtonType.FILLED,
                    onClick = onOpenSettingsClick,
                    modifier = Modifier.fillMaxWidth()
                )

            },
            modifier = modifier.padding(horizontal = 16.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun ManualPermissionLayoutPreview() {
    SmartStepTheme {
        ManualPermissionLayout(
            sheetState = rememberModalBottomSheetState(),
            onOpenSettingsClick = {}
        )
    }
}