package dev.mamkin.smartstep.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.smartstep.core.domain.model.Gender
import dev.mamkin.smartstep.core.domain.model.HeightUnit
import dev.mamkin.smartstep.core.domain.model.UnitConverter
import dev.mamkin.smartstep.core.domain.model.UnitSystem
import dev.mamkin.smartstep.core.domain.model.WeightUnit
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import dev.mamkin.smartstep.feature.settings.presentation.components.HeightPickerAction
import dev.mamkin.smartstep.feature.settings.presentation.components.WeightPickerAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadInitialData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.GenderButtonClicked -> toggleGenderDropdown()
            SettingsAction.GenderDropdownDismissed -> toggleGenderDropdown()
            is SettingsAction.GenderSelected -> onGenderSelected(action.gender)
            SettingsAction.HeightButtonClicked -> showHeightPicker()
            is SettingsAction.HeightPicker -> onHeightPickerAction(action.action)
            SettingsAction.WeightButtonClicked -> showWeightPicker()
            is SettingsAction.WeightPicker -> onWeightPickerAction(action.action)
            SettingsAction.StartClicked -> saveProfile()
            SettingsAction.SkipClicked -> Unit
        }
    }

    private suspend fun loadInitialData() {
        val gender = userProfileRepository.getGender().first()
        val heightCm = userProfileRepository.getHeight().first()
        val weightKg = userProfileRepository.getWeight().first()
        val heightUnit = userProfileRepository.getHeightUnit().first()
        val weightUnit = userProfileRepository.getWeightUnit().first()

        _state.update { current ->
            val cm = heightCm ?: current.heightPickerState.selectedCmValue
            val (ft, inch) = UnitConverter.cmToFtIn(cm)
            val kg = weightKg?.toInt() ?: current.weightPickerState.selectedKgValue
            val lb = UnitConverter.kgToLb(kg)

            current.copy(
                selectedGender = gender ?: current.selectedGender,
                heightPickerState = current.heightPickerState.copy(
                    selectedHeightUnit = heightUnit,
                    selectedCmValue = cm,
                    selectedFtValue = ft,
                    selectedInchValue = inch,
                ),
                weightPickerState = current.weightPickerState.copy(
                    selectedWeightUnit = weightUnit,
                    selectedKgValue = kg,
                    selectedLbValue = lb,
                ),
            )
        }
    }

    private fun toggleGenderDropdown() {
        _state.update {
            it.copy(
                genderDropdownExpanded = !it.genderDropdownExpanded
            )
        }
    }

    private fun onGenderSelected(gender: Gender) {
        _state.update {
            it.copy(
                selectedGender = gender
            )
        }
    }

    private fun saveProfile() {
        val currentState = _state.value
        viewModelScope.launch {
            userProfileRepository.saveGender(currentState.selectedGender)
            userProfileRepository.saveHeight(currentState.heightPickerState.selectedCmValue)
            userProfileRepository.saveWeight(currentState.weightPickerState.selectedKgValue.toFloat())
            val unitSystem = when (currentState.heightPickerState.selectedHeightUnit) {
                HeightUnit.CENTIMETER -> UnitSystem.METRIC
                HeightUnit.FOOT_INCH -> UnitSystem.IMPERIAL
            }
            userProfileRepository.saveUnit(unitSystem)
        }
    }

    private fun showHeightPicker() {
        _state.update { it.copy(heightPickerVisible = true) }
    }

    private fun showWeightPicker() {
        _state.update { it.copy(weightPickerVisible = true) }
    }

    private fun onWeightPickerAction(action: WeightPickerAction) {
        when (action) {
            WeightPickerAction.Dismiss -> {
                _state.update { it.copy(weightPickerVisible = false) }
            }
            is WeightPickerAction.WeightUnitChanged -> {
                _state.update {
                    val wp = it.weightPickerState
                    val updatedWeight = when (action.unit) {
                        WeightUnit.KILOGRAM -> {
                            val kg = UnitConverter.lbToKg(wp.selectedLbValue)
                            wp.copy(selectedWeightUnit = action.unit, selectedKgValue = kg)
                        }
                        WeightUnit.POUND -> {
                            val lb = UnitConverter.kgToLb(wp.selectedKgValue)
                            wp.copy(selectedWeightUnit = action.unit, selectedLbValue = lb)
                        }
                    }
                    val syncedHeightUnit = when (action.unit) {
                        WeightUnit.KILOGRAM -> HeightUnit.CENTIMETER
                        WeightUnit.POUND -> HeightUnit.FOOT_INCH
                    }
                    val hp = it.heightPickerState
                    val updatedHeight = if (hp.selectedHeightUnit != syncedHeightUnit) {
                        when (syncedHeightUnit) {
                            HeightUnit.CENTIMETER -> {
                                val cm = UnitConverter.ftInToCm(hp.selectedFtValue, hp.selectedInchValue)
                                hp.copy(selectedHeightUnit = syncedHeightUnit, selectedCmValue = cm)
                            }
                            HeightUnit.FOOT_INCH -> {
                                val (ft, inch) = UnitConverter.cmToFtIn(hp.selectedCmValue)
                                hp.copy(selectedHeightUnit = syncedHeightUnit, selectedFtValue = ft, selectedInchValue = inch)
                            }
                        }
                    } else hp
                    it.copy(weightPickerState = updatedWeight, heightPickerState = updatedHeight)
                }
            }
            is WeightPickerAction.KgValueChanged -> {
                _state.update {
                    val lb = UnitConverter.kgToLb(action.value)
                    it.copy(
                        weightPickerState = it.weightPickerState.copy(
                            selectedKgValue = action.value,
                            selectedLbValue = lb
                        )
                    )
                }
            }
            is WeightPickerAction.LbValueChanged -> {
                _state.update {
                    val kg = UnitConverter.lbToKg(action.value)
                    it.copy(
                        weightPickerState = it.weightPickerState.copy(
                            selectedLbValue = action.value,
                            selectedKgValue = kg
                        )
                    )
                }
            }
        }
    }

    private fun onHeightPickerAction(action: HeightPickerAction) {
        when (action) {
            HeightPickerAction.Dismiss -> {
                _state.update { it.copy(heightPickerVisible = false) }
            }
            is HeightPickerAction.HeightUnitChanged -> {
                _state.update {
                    val hp = it.heightPickerState
                    val updatedHeight = when (action.unit) {
                        HeightUnit.CENTIMETER -> {
                            val cm = UnitConverter.ftInToCm(hp.selectedFtValue, hp.selectedInchValue)
                            hp.copy(selectedHeightUnit = action.unit, selectedCmValue = cm)
                        }
                        HeightUnit.FOOT_INCH -> {
                            val (ft, inch) = UnitConverter.cmToFtIn(hp.selectedCmValue)
                            hp.copy(selectedHeightUnit = action.unit, selectedFtValue = ft, selectedInchValue = inch)
                        }
                    }
                    val syncedWeightUnit = when (action.unit) {
                        HeightUnit.CENTIMETER -> WeightUnit.KILOGRAM
                        HeightUnit.FOOT_INCH -> WeightUnit.POUND
                    }
                    val wp = it.weightPickerState
                    val updatedWeight = if (wp.selectedWeightUnit != syncedWeightUnit) {
                        when (syncedWeightUnit) {
                            WeightUnit.KILOGRAM -> {
                                val kg = UnitConverter.lbToKg(wp.selectedLbValue)
                                wp.copy(selectedWeightUnit = syncedWeightUnit, selectedKgValue = kg)
                            }
                            WeightUnit.POUND -> {
                                val lb = UnitConverter.kgToLb(wp.selectedKgValue)
                                wp.copy(selectedWeightUnit = syncedWeightUnit, selectedLbValue = lb)
                            }
                        }
                    } else wp
                    it.copy(heightPickerState = updatedHeight, weightPickerState = updatedWeight)
                }
            }
            is HeightPickerAction.CmValueChanged -> {
                _state.update {
                    val (ft, inch) = UnitConverter.cmToFtIn(action.value)
                    it.copy(
                        heightPickerState = it.heightPickerState.copy(
                            selectedCmValue = action.value,
                            selectedFtValue = ft,
                            selectedInchValue = inch
                        )
                    )
                }
            }
            is HeightPickerAction.FtValueChanged -> {
                _state.update {
                    val cm = UnitConverter.ftInToCm(action.value, it.heightPickerState.selectedInchValue)
                    it.copy(
                        heightPickerState = it.heightPickerState.copy(
                            selectedFtValue = action.value,
                            selectedCmValue = cm
                        )
                    )
                }
            }
            is HeightPickerAction.InchValueChanged -> {
                _state.update {
                    val cm = UnitConverter.ftInToCm(it.heightPickerState.selectedFtValue, action.value)
                    it.copy(
                        heightPickerState = it.heightPickerState.copy(
                            selectedInchValue = action.value,
                            selectedCmValue = cm
                        )
                    )
                }
            }
        }
    }

}