package dev.mamkin.smartstep.feature.settings.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.onLayoutRectChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.domain.model.Gender
import dev.mamkin.smartstep.core.presentation.components.PickerButton
import dev.mamkin.smartstep.core.presentation.theme.AppTheme

@Composable
fun GenderPicker(
    dropdownExpanded: Boolean,
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit,
    onDismissDropdown: () -> Unit,
    onGenderButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var width by remember { mutableStateOf(0) }
    Box(
        modifier = modifier.onLayoutRectChanged {
            width = it.width
        }
    ) {
        PickerButton(
            label = "Gender",
            text = selectedGender.toString(),
            onClick = onGenderButtonClicked
        )
        DropdownMenu(
            modifier = Modifier.width(with(LocalDensity.current) { width.toDp() }),
            expanded = dropdownExpanded,
            containerColor = AppTheme.colors.backgroundWhite,
            shape = RoundedCornerShape(8.dp),
            offset = DpOffset(y = 8.dp, x = 0.dp),
            onDismissRequest = onDismissDropdown
        ) {
            Gender.entries.forEach {
                DropdownItem(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    isSelected = it == selectedGender,
                    text = it.toString(),
                    onClick = {
                        onDismissDropdown()
                        onGenderSelected(it)
                    }
                )
            }
        }
    }

}