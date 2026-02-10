package dev.mamkin.smartstep.feature.settings.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.mamkin.smartstep.core.presentation.components.PickerButton
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium
import dev.mamkin.smartstep.feature.settings.presentation.components.GenderPicker

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "My profile",
                        style = MaterialTheme.typography.titleMedium,
                        color = AppTheme.colors.textPrimary
                    )
                },
                actions = {
                    TextButton(
                        onClick = {

                        }
                    ) {
                        Text(
                            text = "Skip",
                            style = MaterialTheme.typography.bodyLargeMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar (
                contentPadding = PaddingValues(16.dp)
            ) {
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.bodyLargeMedium,
                    )
                }
            }
        },
        containerColor = AppTheme.colors.backgroundSecondary,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 32.dp, end = 16.dp, start = 16.dp)
        ) {
            Text(
                text = "This information helps calculate your activity more accurately.",
                style = MaterialTheme.typography.bodyLargeMedium,
                color = AppTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.colors.backgroundWhite,
                ),
                border = BorderStroke(1.dp, AppTheme.colors.strokeMain)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GenderPicker(
                        dropdownExpanded = state.genderDropdownExpanded,
                        selectedGender = state.selectedGender,
                        onGenderSelected = {
                            onAction(SettingsAction.GenderSelected(it))
                        },
                        onDismissDropdown = {
                            onAction(SettingsAction.GenderDropdownDismissed)
                        },
                        onGenderButtonClicked = {
                            onAction(SettingsAction.GenderButtonClicked)
                        },
                        modifier = Modifier
                    )
                    PickerButton(
                        label = "Height",
                        text = "170 cm",
                        onClick = {}
                    )
                    PickerButton(
                        label = "Weight",
                        text = "60 kg",
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SmartStepTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}