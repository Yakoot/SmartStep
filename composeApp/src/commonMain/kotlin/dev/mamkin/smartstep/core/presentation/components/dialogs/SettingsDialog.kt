package dev.mamkin.smartstep.core.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.components.AppButton
import dev.mamkin.smartstep.core.presentation.components.AppButtonType
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.app_will_fully_close
import smartstep.composeapp.generated.resources.ok
import smartstep.composeapp.generated.resources.shutdown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier) {

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth().wrapContentHeight().background(
            Color.White,
            RoundedCornerShape(24.dp)
        )
    ) {

        Column(
            modifier = modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(Res.drawable.shutdown),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(Res.string.app_will_fully_close),
                color = AppTheme.colors.textSecondary,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            AppButton(
                text = stringResource(Res.string.ok),
                type = AppButtonType.FILLED,
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}


@Composable
@Preview(showBackground = true)
fun SettingsDialogPreview(modifier: Modifier = Modifier) {
    SmartStepTheme {
        SettingsDialog(onDismiss = {})
    }
}