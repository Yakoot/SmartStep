package dev.mamkin.smartstep.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.theme.LocalAppColors
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium
import dev.mamkin.smartstep.core.presentation.theme.bodyMediumMedium
import dev.mamkin.smartstep.feature.home.presentation.model.EditStepDate
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.cancel
import smartstep.composeapp.generated.resources.edit_steps_description
import smartstep.composeapp.generated.resources.edit_steps_title
import smartstep.composeapp.generated.resources.reset
import smartstep.composeapp.generated.resources.save

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStepsDialog(
    selectedDate: EditStepDate,
    selectedSteps: Int,
    onDismissRequest: () -> Unit,
    onDateClick: () -> Unit,
    onStepsChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .width(328.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(colors.backgroundSecondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.backgroundSecondary)
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(Res.string.edit_steps_title),
                style = MaterialTheme.typography.titleMedium,
                color = colors.textPrimary
            )

            Text(
                text = stringResource(Res.string.edit_steps_description),
                style = MaterialTheme.typography.bodyMediumMedium,
                color = colors.textSecondary
            )

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onDateClick)
                    .border(
                        width = 1.dp,
                        color = colors.strokeMain,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(colors.backgroundSecondary)
                    .padding(
                        vertical = 8.dp,
                        horizontal = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Date",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )

                    Text(
                        text = selectedDate.format(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.textPrimary
                    )
                }

                IconButton(
                    onClick = onDateClick,
                    modifier = Modifier.size(24.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = colors.textPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = "$selectedSteps",
                onValueChange = onStepsChange,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = colors.textSecondary,
                    unfocusedLabelColor = colors.textSecondary,
                    focusedTextColor = colors.textPrimary,
                    unfocusedTextColor = colors.textPrimary,
                    focusedContainerColor = colors.backgroundSecondary,
                    unfocusedContainerColor = colors.backgroundSecondary,
                    focusedBorderColor = colors.strokeMain,
                    unfocusedBorderColor = colors.strokeMain,
                ),
                label = {
                    Text(
                        text = "Steps",
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(Modifier.height(20.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = stringResource(Res.string.cancel),
                        style = MaterialTheme.typography.bodyLargeMedium,
                        color = colors.buttonPrimary
                    )
                }

                TextButton(
                    onClick = onSaveClick
                ) {
                    Text(
                        text = stringResource(Res.string.save),
                        style = MaterialTheme.typography.bodyLargeMedium,
                        color = colors.buttonPrimary
                    )
                }
            }
        }
    }
}