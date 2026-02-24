package dev.mamkin.smartstep.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.theme.LocalAppColors
import dev.mamkin.smartstep.core.presentation.theme.bodyLargeMedium
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.cancel
import smartstep.composeapp.generated.resources.reset
import smartstep.composeapp.generated.resources.reset_steps_description

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetStepsDialog(
    onDismissRequest : () -> Unit,
    onReset : () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current
    BasicAlertDialog(
        modifier = modifier
            .width(328.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(colors.backgroundSecondary),
        onDismissRequest = onDismissRequest,
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.backgroundSecondary)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.reset_steps_description),
                style = MaterialTheme.typography.bodyLargeMedium,
                color = colors.textPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
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
                    onClick = onReset
                ) {
                    Text(
                        text = stringResource(Res.string.reset),
                        style = MaterialTheme.typography.bodyLargeMedium,
                        color = colors.buttonPrimary
                    )
                }
            }
        }
    }
}