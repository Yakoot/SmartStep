package dev.mamkin.smartstep.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.smartstep.core.domain.model.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
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

}