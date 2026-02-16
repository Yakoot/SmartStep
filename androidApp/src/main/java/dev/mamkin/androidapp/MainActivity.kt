package dev.mamkin.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mamkin.smartstep.App
import dev.mamkin.smartstep.app.MainViewModel
import dev.mamkin.smartstep.core.presentation.utils.ActivityHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { !mainViewModel.isReady }

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        // This observer tells the ActivityHolder about the activity's lifecycle.
        val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                // When the activity is in the foreground, set it in the holder.
                ActivityHolder.setActivity(this@MainActivity)
            }
            override fun onPause(owner: LifecycleOwner) {
                // When the activity leaves the foreground, clear the reference.
                ActivityHolder.clearActivity(this@MainActivity)
            }
        }
        lifecycle.addObserver(lifecycleObserver)
        setContent {
            val initialRoute by mainViewModel.initialRoute.collectAsStateWithLifecycle()
            initialRoute?.let {
                App(initialRoute = it)
            }
        }
    }
}
