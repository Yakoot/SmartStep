package dev.mamkin.smartstep.core.presentation.components.layouts


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.background_access_message
import smartstep.composeapp.generated.resources.background_access_recommended
import smartstep.composeapp.generated.resources.btn_continue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackgroundAccessRecommendedLayout(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AdaptiveOverlay(sheetState = sheetState, onDismiss = onDismiss) {
        BaseSkeleton(
            title = {
                Text(
                    text = stringResource(Res.string.background_access_recommended),
                    color = AppTheme.colors.textPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            },
            body = {
                Text(
                    text = stringResource(Res.string.background_access_message),
                    color = AppTheme.colors.textSecondary,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            },
            footer = {

                AppButton(
                    text = stringResource(Res.string.btn_continue),
                    type = AppButtonType.FILLED,
                    onClick = onConfirm,
                    modifier = Modifier.fillMaxWidth()
                )

            }, modifier = modifier.padding(horizontal = 16.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun BackgroundAccessRecommendedLayoutPreview(modifier: Modifier = Modifier) {
    SmartStepTheme {
        BackgroundAccessRecommendedLayout(
            sheetState = rememberModalBottomSheetState(),
            onDismiss = {},
            onConfirm = {})
    }
}