package dev.mamkin.smartstep.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import dev.mamkin.smartstep.core.presentation.theme.bodyMediumMedium

@Composable
fun SingleChoiceSegmentedButton(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    options: List<String>,
    onOptionSelected: (Int) -> Unit,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                border = BorderStroke(1.dp, AppTheme.colors.strokeMain),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = AppTheme.colors.buttonSecondary,
                    activeContentColor = AppTheme.colors.textPrimary,
                    inactiveContainerColor = AppTheme.colors.backgroundWhite,
                    inactiveContentColor = AppTheme.colors.textPrimary
                ),
                onClick = { onOptionSelected(index) },
                selected = index == selectedIndex,
                label = { Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMediumMedium
                ) }
            )
        }
    }
}

@Preview
@Composable
private fun SingleChoiceSegmentedButtonPreview() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    SmartStepTheme {
        SingleChoiceSegmentedButton(
            modifier = Modifier.width(400.dp),
            options = listOf("Option 1", "Option 2"),
            selectedIndex = selectedIndex,
            onOptionSelected = { selectedIndex = it }
        )
    }
}
