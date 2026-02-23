package dev.mamkin.smartstep.feature.home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat
import dev.mamkin.smartstep.core.presentation.theme.AppColors
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.InterFontFamily
import dev.mamkin.smartstep.core.presentation.theme.LocalAppColors
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import dev.mamkin.smartstep.core.presentation.theme.titleAccent
import dev.mamkin.smartstep.feature.home.presentation.HomeAction
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.ic_continue
import smartstep.composeapp.generated.resources.ic_edit
import smartstep.composeapp.generated.resources.ic_location
import smartstep.composeapp.generated.resources.ic_pause
import smartstep.composeapp.generated.resources.ic_sneakers
import smartstep.composeapp.generated.resources.ic_time
import smartstep.composeapp.generated.resources.ic_weight
import smartstep.composeapp.generated.resources.location
import smartstep.composeapp.generated.resources.steps_card_goal
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun StepsCard(
    stats: DailyStat,
    isTrackingPaused: Boolean,
    goal: Int,
    onAction: (HomeAction) -> Unit,
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
            TopBarActions(
                onAction = onAction,
                isTrackingPaused = isTrackingPaused
            )

            StepStats(
                stats = stats,
                isTrackingPaused = isTrackingPaused,
                goal = goal,
                progressBgColor = progressBgColor,
                progressIndicatorColor = progressIndicatorColor
            )

            Spacer(Modifier.height(32.dp))

            AdditionalInfos(
                stats = stats
            )
        }
    }
}

@Composable
fun AdditionalInfos(
    stats: DailyStat,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AdditionalInfoItem(
            icon = Res.drawable.ic_location,
            title = stats.kms.roundTo(2).toString(),
            suffix = "km",
        )

        Spacer(Modifier.weight(1f))

        AdditionalInfoItem(
            icon = Res.drawable.ic_weight,
            title = stats.kcal.roundTo(1).toString(),
            suffix = "kcal",
        )

        Spacer(Modifier.weight(1f))

        AdditionalInfoItem(
            icon = Res.drawable.ic_time,
            title = stats.minutes.roundTo(1).toString(),
            suffix = "min",
        )
    }
}

@Composable
fun AdditionalInfoItem(
    icon: DrawableResource,
    title: String,
    suffix: String,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colors.backgroundWhite20),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = colors.textWhite
            )
        }

        val text = buildAnnotatedString {
            withStyle(SpanStyle(
                color = colors.textWhite,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )) {
                append(title)
            }

            withStyle(SpanStyle(
                color = colors.buttonSecondary,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )) {
                append(suffix)
            }
        }

        Text(
            text = text
        )
    }
}

@Composable
private fun TopBarActions(
    onAction: (HomeAction) -> Unit,
    isTrackingPaused: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(
                        AppTheme.colors.backgroundWhite20,
                        CircleShape
                    )
                    .clickable(onClick = {
                        onAction(HomeAction.OnEditStepsClick)
                    }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.ic_edit),
                    tint = AppTheme.colors.textWhite,
                    contentDescription = null
                )
            }

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(
                        AppTheme.colors.backgroundWhite20,
                        CircleShape
                    )
                    .clickable(onClick = {
                        onAction(HomeAction.OnToggleStepTracking)
                    }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        if (isTrackingPaused) {
                            Res.drawable.ic_continue
                        } else Res.drawable.ic_pause
                    ),
                    contentDescription = null,
                    tint = AppTheme.colors.textWhite,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun StepStats(
    stats: DailyStat,
    isTrackingPaused: Boolean,
    goal: Int,
    progressBgColor: Color,
    progressIndicatorColor: Color
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stats.steps.toString(),
            style = MaterialTheme.typography.titleAccent,
            color = if (isTrackingPaused) {
                AppTheme.colors.backgroundWhite20
            } else AppTheme.colors.textWhite
        )
        Text(
            text = if (isTrackingPaused) {
                "Paused"
            } else stringResource(Res.string.steps_card_goal, goal),
            style = MaterialTheme.typography.titleMedium,
            color = AppTheme.colors.textWhite90
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
    ) {
        val width = size.width
        val height = size.height
        val progress = stats.steps.toFloat() / goal.toFloat()

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

@Preview
@Composable
private fun Preview() {
    SmartStepTheme {
        StepsCard(
            stats = DailyStat(
                epochDay = 0L,
                steps = 1000,
                kms = .1,
                kcal = .1,
                minutes = .1
            ),
            goal = 6000,
            onAction = {

            },
            isTrackingPaused = false
        )
    }
}

fun Double.roundTo(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return round(this * factor) / factor
}