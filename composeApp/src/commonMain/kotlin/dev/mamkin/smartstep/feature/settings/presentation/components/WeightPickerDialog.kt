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
import dev.mamkin.smartstep.core.domain.model.WeightUnit
import dev.mamkin.smartstep.core.presentation.components.AppButton
import dev.mamkin.smartstep.core.presentation.components.AppButtonType
import dev.mamkin.smartstep.core.presentation.components.pickers.ScrollablePickerColumn
import dev.mamkin.smartstep.core.presentation.components.SegmentedButton
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme

data class WeightPickerState(
    val selectedWeightUnit: WeightUnit,
    val selectedKgValue: Int,
    val selectedLbValue: Int,
    val availableKgValues: List<Int>,
    val availableLbValues: List<Int>,
)

sealed interface WeightPickerAction {
    data object Dismiss : WeightPickerAction
    data class WeightUnitChanged(val unit: WeightUnit) : WeightPickerAction
    data class KgValueChanged(val value: Int) : WeightPickerAction
    data class LbValueChanged(val value: Int) : WeightPickerAction
}

@Composable
fun WeightPickerDialog(
    state: WeightPickerState,
    onAction: (WeightPickerAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = { onAction(WeightPickerAction.Dismiss) }
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
                    text = "Weight",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.textPrimary
                )
                Text(
                    modifier = paddingModifier,
                    text = "Used to calculate calories",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.textSecondary
                )
                SegmentedButton(
                    modifier = paddingModifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    options = listOf("kg", "lb"),
                    selectedIndex = state.selectedWeightUnit.ordinal,
                    onOptionSelected = { onAction(WeightPickerAction.WeightUnitChanged(WeightUnit.entries[it])) }
                )
                when (state.selectedWeightUnit) {
                    WeightUnit.KILOGRAM -> {
                        ScrollablePickerColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 28.dp),
                            value = state.selectedKgValue.toString(),
                            values = state.availableKgValues.map { it.toString() },
                            onValueChange = {
                                onAction(WeightPickerAction.KgValueChanged(it.toInt()))
                            }
                        )
                    }

                    WeightUnit.POUND -> {
                        ScrollablePickerColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 28.dp),
                            value = state.selectedLbValue.toString(),
                            values = state.availableLbValues.map { it.toString() },
                            onValueChange = {
                                onAction(WeightPickerAction.LbValueChanged(it.toInt()))
                            }
                        )
                    }
                }

                Row(
                    modifier = paddingModifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                ) {
                    AppButton(
                        text = "Cancel",
                        onClick = { onAction(WeightPickerAction.Dismiss) },
                        type = AppButtonType.TEXT
                    )
                    AppButton(
                        text = "Ok",
                        onClick = { onAction(WeightPickerAction.Dismiss) },
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
        WeightPickerDialog(
            state = WeightPickerState(
                selectedWeightUnit = WeightUnit.KILOGRAM,
                selectedKgValue = 60,
                selectedLbValue = 132,
                availableKgValues = (30..200).toList(),
                availableLbValues = (66..440).toList(),
            ),
            onAction = {},
        )
    }
}