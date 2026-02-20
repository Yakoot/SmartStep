package dev.mamkin.smartstep.feature.settings.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.mamkin.smartstep.core.domain.model.HeightUnit
import dev.mamkin.smartstep.core.presentation.components.AppButton
import dev.mamkin.smartstep.core.presentation.components.AppButtonType
import dev.mamkin.smartstep.core.presentation.components.pickers.ScrollablePickerColumn
import dev.mamkin.smartstep.core.presentation.components.SegmentedButton
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme

data class HeightPickerState(
    val selectedHeightUnit: HeightUnit,
    val selectedCmValue: Int,
    val selectedFtValue: Int,
    val selectedInchValue: Int,
    val availableCmValues: List<Int>,
    val availableFtValues: List<Int>,
    val availableInchValues: List<Int>,
)

sealed interface HeightPickerAction {
    data object Dismiss : HeightPickerAction
    data class HeightUnitChanged(val unit: HeightUnit) : HeightPickerAction
    data class CmValueChanged(val value: Int) : HeightPickerAction
    data class FtValueChanged(val value: Int) : HeightPickerAction
    data class InchValueChanged(val value: Int) : HeightPickerAction
}

@Composable
fun HeightPickerDialog(
    state: HeightPickerState,
    onAction: (HeightPickerAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = { onAction(HeightPickerAction.Dismiss) }
    ) {
        Surface(
            color = AppTheme.colors.backgroundSecondary,
            modifier = modifier.width(328.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            val paddingModifier = Modifier.padding(horizontal = 24.dp)
            Column(
                modifier = Modifier.padding(top = 24.dp, bottom = 20.dp)
            ) {
                Text(
                    modifier = paddingModifier,
                    text = "Height",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.textPrimary
                )
                Text(
                    modifier = paddingModifier,
                    text = "Used to calculate distance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.textSecondary
                )
                SegmentedButton(
                    modifier = paddingModifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    options = listOf("cm", "ft/in"),
                    selectedIndex = state.selectedHeightUnit.ordinal,
                    onOptionSelected = { onAction(HeightPickerAction.HeightUnitChanged(HeightUnit.entries[it])) }
                )
                when (state.selectedHeightUnit) {
                    HeightUnit.CENTIMETER -> {
                        ScrollablePickerColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 28.dp),
                            value = state.selectedCmValue.toString(),
                            values = state.availableCmValues.map { it.toString() },
                            onValueChange = {
                                onAction(HeightPickerAction.CmValueChanged(it.toInt()))
                            }
                        )
                    }

                    HeightUnit.FOOT_INCH -> {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ScrollablePickerColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = 28.dp),
                                selectedValueUnitText = "ft",
                                value = state.selectedFtValue.toString(),
                                values = state.availableFtValues.map { it.toString() },
                                onValueChange = {
                                    onAction(HeightPickerAction.FtValueChanged(it.toInt()))
                                }
                            )
                            ScrollablePickerColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = 28.dp),
                                selectedValueUnitText = "in",
                                value = state.selectedInchValue.toString(),
                                values = state.availableInchValues.map { it.toString() },
                                onValueChange = {
                                    onAction(HeightPickerAction.InchValueChanged(it.toInt()))
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = paddingModifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                ) {
                    AppButton(
                        text = "Cancel",
                        onClick = { onAction(HeightPickerAction.Dismiss) },
                        type = AppButtonType.TEXT
                    )
                    AppButton(
                        text = "Ok",
                        onClick = { onAction(HeightPickerAction.Dismiss) },
                        type = AppButtonType.TEXT
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SmartStepTheme {
        HeightPickerDialog(
            state = HeightPickerState(
                selectedHeightUnit = HeightUnit.FOOT_INCH,
                selectedCmValue = 175,
                selectedFtValue = 10,
                selectedInchValue = 10,
                availableCmValues = (150..200).toList(),
                availableFtValues = (150..200).toList(),
                availableInchValues = (150..200).toList(),
            ),
            onAction = {},
        )
    }
}
