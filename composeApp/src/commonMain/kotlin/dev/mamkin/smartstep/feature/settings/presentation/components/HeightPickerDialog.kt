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
import dev.mamkin.smartstep.core.presentation.components.ScrollablePickerColumn
import dev.mamkin.smartstep.core.presentation.components.SegmentedButton
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme

@Composable
fun HeightPickerDialog(
    onDismiss: () -> Unit,
    selectedHeightUnit: HeightUnit,
    onHeightUnitChanged: (HeightUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            color = AppTheme.colors.backgroundSecondary,
            modifier = Modifier.width(328.dp),
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
                    selectedIndex = selectedHeightUnit.ordinal,
                    onOptionSelected = { onHeightUnitChanged(HeightUnit.entries[it]) }
                )
                when (selectedHeightUnit) {
                    HeightUnit.CENTIMETER -> {
                        ScrollablePickerColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 28.dp),
                            value = "175",
                            values = (150..200).map { it.toString() },
                            onValueChange = {}
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
                                value = "175",
                                selectedValueUnitText = "ft",
                                values = (150..200).map { it.toString() },
                                onValueChange = {}
                            )
                            ScrollablePickerColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = 28.dp),
                                value = "175",
                                selectedValueUnitText = "in",

                                values = (150..200).map { it.toString() },
                                onValueChange = {}
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
                        onClick = onDismiss,
                        type = AppButtonType.TEXT
                    )
                    AppButton(
                        text = "Ok",
                        onClick = onDismiss,
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
            selectedHeightUnit = HeightUnit.FOOT_INCH,
            onHeightUnitChanged = {},
            onDismiss = {}
        )
    }
}