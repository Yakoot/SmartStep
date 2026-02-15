package dev.mamkin.smartstep.feature.home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import dev.mamkin.smartstep.core.presentation.theme.titleAccent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.ic_sneakers
import smartstep.composeapp.generated.resources.steps_card_goal

@Composable
fun StepsCard(
    steps: Int,
    goal: Int,
    modifier: Modifier = Modifier
) {
    val progressBgColor = AppTheme.colors.backgroundWhite20
    val progressIndicatorColor = AppTheme.colors.textWhite
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.buttonPrimary
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(AppTheme.colors.backgroundWhite20, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(Res.drawable.ic_sneakers),
                    tint = AppTheme.colors.textWhite,
                    contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = steps.toString(),
                    style = MaterialTheme.typography.titleAccent,
                    color = AppTheme.colors.textWhite
                )
                Text(
                    text = stringResource(Res.string.steps_card_goal, goal),
                    style = MaterialTheme.typography.titleMedium,
                    color = AppTheme.colors.textWhite90
                )
            }

            Canvas(
                modifier = Modifier.fillMaxWidth().height(12.dp)
            ) {
                val width = size.width
                val height = size.height
                val progress = steps.toFloat() / goal.toFloat()
                drawRoundRect(
                    color = progressBgColor,
                    cornerRadius = CornerRadius(height)
                )
                drawRoundRect(
                    topLeft = Offset(x = 2.dp.toPx(), y = 2.dp.toPx()),
                    size = Size(
                        width = (width - 4.dp.toPx()) * progress,
                        height = 8.dp.toPx()
                        ),
                    color = progressIndicatorColor,
                    cornerRadius = CornerRadius(height)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SmartStepTheme {
        StepsCard(
            steps = 1000,
            goal = 6000,
        )
    }
}