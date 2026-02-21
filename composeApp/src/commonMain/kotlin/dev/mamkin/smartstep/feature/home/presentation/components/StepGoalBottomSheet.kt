package dev.mamkin.smartstep.feature.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.components.AppButton
import dev.mamkin.smartstep.core.presentation.components.AppButtonType
import dev.mamkin.smartstep.core.presentation.components.pickers.ScrollablePickerColumn
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.cancel
import smartstep.composeapp.generated.resources.drawer_item_step_goal
import smartstep.composeapp.generated.resources.save

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepGoalBottomSheet(
    stepGoal: Int?,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSave: (newStepGoal: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedValue by remember {
        mutableStateOf(stepGoal?.toString() ?: 1000.toString())
    }

    val numberRange = remember { (1000..40000 step 100).map { it.toString() } }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = stringResource(Res.string.drawer_item_step_goal),
                color = AppTheme.colors.textPrimary,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            ScrollablePickerColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                value = selectedValue,
                values = numberRange,
                onValueChange = {
                    selectedValue = it
                }
            )

            AppButton(
                text = stringResource(Res.string.save),
                type = AppButtonType.FILLED,
                onClick = { onSave(selectedValue.toInt()) },
                modifier = Modifier.fillMaxWidth()
            )
            AppButton(
                text = stringResource(Res.string.cancel),
                type = AppButtonType.TEXT,
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun StepGoalBottomSheetPreview(modifier: Modifier = Modifier) {
    SmartStepTheme {
        StepGoalBottomSheet(
            stepGoal = 1000,
            sheetState = rememberModalBottomSheetState(),
            onDismiss = {},
            onSave = {},
            modifier = modifier
        )
    }
}

