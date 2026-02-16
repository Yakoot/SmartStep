package dev.mamkin.smartstep.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import dev.mamkin.smartstep.core.presentation.utils.currentDeviceConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveOverlay(
    sheetState: SheetState,
    dialogProperties: ModalBottomSheetProperties,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val currentDeviceConfiguration = currentDeviceConfiguration()

    if (currentDeviceConfiguration.isMobile) {
        ModalBottomSheet(
            sheetState = sheetState,
            properties = dialogProperties,
            onDismissRequest = onDismiss
        ) {
            content()
        }
    } else {
        BasicAlertDialog(
            onDismissRequest = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            content()
        }
    }
}