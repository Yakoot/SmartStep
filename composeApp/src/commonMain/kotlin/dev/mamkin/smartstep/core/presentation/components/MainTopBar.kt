package dev.mamkin.smartstep.core.presentation.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.app_name
import smartstep.composeapp.generated.resources.ic_menu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(onDrawerOpen: () -> Unit, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.backgroundMain
        ),
        title = {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.titleMedium,
                color = AppTheme.colors.textPrimary
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onDrawerOpen
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_menu),
                    tint = AppTheme.colors.textPrimary,
                    contentDescription = "Menu"
                )
            }
        },
        modifier = modifier
    )
}