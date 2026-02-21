package dev.mamkin.smartstep.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.components.pickers.ScrollablePickerColumn
import dev.mamkin.smartstep.core.presentation.theme.LocalAppColors
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium
import dev.mamkin.smartstep.feature.home.presentation.model.EditStepDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.cancel
import smartstep.composeapp.generated.resources.reset
import smartstep.composeapp.generated.resources.save
import kotlin.time.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDateDialog(
    initialData: EditStepDate,
    onSave: (EditStepDate) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current
    val currentYear = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    }

    var data by retain {
        mutableStateOf(
            EditStepDate(
                year = initialData.year,
                month = initialData.month,
                day = initialData.day
            )
        )
    }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(colors.backgroundSecondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.backgroundSecondary)
        ) {
            Text(
                text = "Date",
                style = MaterialTheme.typography.titleMedium,
                color = colors.textPrimary,
                modifier = Modifier.padding(24.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ScrollablePickerColumn(
                    value = data.year.toString(),
                    values = (2000..currentYear).map { it.toString() }.toList(),
                    onValueChange = {
                        data = data.copy(
                            year = it.toInt()
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
                ScrollablePickerColumn(
                    value = data.month.toString(),
                    values = (1..12).map { it.toString() }.toList(),
                    onValueChange = {
                        data = data.copy(
                            month = it.toInt(),
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
                ScrollablePickerColumn(
                    value = data.day.toString(),
                    values = (1..31).map { it.toString() }.toList(),
                    onValueChange = {
                        data = data.copy(
                            day = it.toInt()
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
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
                    onClick = {
                        onSave(data)
                    }
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