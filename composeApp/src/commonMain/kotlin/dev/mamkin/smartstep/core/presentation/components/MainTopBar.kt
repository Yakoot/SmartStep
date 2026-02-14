package dev.mamkin.smartstep.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.app_name

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(onDrawerOpen: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = stringResource(Res.string.app_name)) },
        navigationIcon = {
            IconButton(
                onClick = onDrawerOpen
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        modifier = modifier
    )
}