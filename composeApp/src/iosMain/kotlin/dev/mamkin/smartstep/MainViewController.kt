package dev.mamkin.smartstep

import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.smartstep.app.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

fun MainViewController() = ComposeUIViewController {
    val mainViewModel = koinViewModel<MainViewModel>()
    val initialRoute by mainViewModel.initialRoute.collectAsStateWithLifecycle()
    initialRoute?.let {
        App(initialRoute = it)
    }
}