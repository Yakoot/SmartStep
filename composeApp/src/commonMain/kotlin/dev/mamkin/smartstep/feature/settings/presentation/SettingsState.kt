package dev.mamkin.smartstep.feature.settings.presentation

import dev.mamkin.smartstep.core.domain.model.Gender
import dev.mamkin.smartstep.core.domain.model.HeightUnit
import dev.mamkin.smartstep.core.domain.model.WeightUnit
import dev.mamkin.smartstep.feature.settings.presentation.components.HeightPickerState
import dev.mamkin.smartstep.feature.settings.presentation.components.WeightPickerState

data class SettingsState(
    val selectedGender: Gender = Gender.Female,
    val genderDropdownExpanded: Boolean = false,
    val heightPickerVisible: Boolean = false,
    val heightPickerState: HeightPickerState = HeightPickerState(
        selectedHeightUnit = HeightUnit.CENTIMETER,
        selectedCmValue = 175,
        selectedFtValue = 5,
        selectedInchValue = 9,
        availableCmValues = (100..250).toList(),
        availableFtValues = (3..8).toList(),
        availableInchValues = (0..11).toList(),
    ),
    val weightPickerVisible: Boolean = false,
    val weightPickerState: WeightPickerState = WeightPickerState(
        selectedWeightUnit = WeightUnit.KILOGRAM,
        selectedKgValue = 65,
        selectedLbValue = 143,
        availableKgValues = (30..200).toList(),
        availableLbValues = (66..440).toList(),
    )
)
