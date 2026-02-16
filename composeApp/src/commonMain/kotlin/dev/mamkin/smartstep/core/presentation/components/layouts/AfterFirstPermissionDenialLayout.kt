package dev.mamkin.smartstep.core.presentation.components.layouts


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.components.AdaptiveOverlay
import dev.mamkin.smartstep.core.presentation.components.AppButton
import dev.mamkin.smartstep.core.presentation.components.AppButtonType
import dev.mamkin.smartstep.core.presentation.components.BaseSkeleton
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.allow_access
import smartstep.composeapp.generated.resources.location
import smartstep.composeapp.generated.resources.motion_sensor_permission_first_denial_message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AfterFirstPermissionDenialLayout(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AdaptiveOverlay(sheetState = sheetState, onDismiss = onDismiss) {
        BaseSkeleton(
            title = {
                Icon(
                    painter = painterResource(Res.drawable.location),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            body = {
                Text(
                    text = stringResource(Res.string.motion_sensor_permission_first_denial_message),
                    color = AppTheme.colors.textPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            },
            footer = {
                AppButton(
                    text = stringResource(Res.string.allow_access),
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
fun AfterFirstPermissionDenialLayoutPreview(modifier: Modifier = Modifier) {
    SmartStepTheme {
        AfterFirstPermissionDenialLayout(sheetState = rememberModalBottomSheetState(),onDismiss = {}, onConfirm = {})
    }
}