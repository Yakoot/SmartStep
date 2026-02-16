package dev.mamkin.smartstep.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mamkin.smartstep.app.navigation.SmartStepGraph
import dev.mamkin.smartstep.core.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _initialRoute = MutableStateFlow<SmartStepGraph?>(null)
    val initialRoute: StateFlow<SmartStepGraph?> = _initialRoute.asStateFlow()

    val isReady: Boolean
        get() = _initialRoute.value != null

    init {
        viewModelScope.launch {
            val isSetupCompleted =  userProfileRepository.isProfileSetupCompleted()

            _initialRoute.value = if (isSetupCompleted) {
                SmartStepGraph.HomeScreen
            } else {
                SmartStepGraph.ProfileSetupScreen(true)
            }
        }
    }
}