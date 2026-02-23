package dev.mamkin.smartstep.feature.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.data.local.db.entity.DailyStat
import dev.mamkin.smartstep.core.presentation.model.WeeklyDay
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.LocalAppColors
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import dev.mamkin.smartstep.core.presentation.theme.bodyMediumMedium

@Composable
fun DailyAverageCard(
    days: List<WeeklyDay>,
    goalSteps: Int,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current

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
            Text(
                text = "Daily Average: ${days.dailyAverage()} steps",
                style = MaterialTheme.typography.titleMedium,
                color = colors.buttonSecondary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                days.forEach { day ->
                    val progress: Float = if (goalSteps > 0) {
                        (day.dailyStat.steps.toFloat() / goalSteps).coerceIn(0f, 1f)
                    } else {
                        0f
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator(
                            progress = { progress },
                            modifier = Modifier,
                            color = colors.additionalGreen,
                            strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
                            trackColor = colors.backgroundWhite,
                            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = day.dayName,
                            style = MaterialTheme.typography.bodyMediumMedium,
                            color = colors.textWhite
                        )

                        Text(
                            text = day.dailyStat.steps.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.buttonSecondary
                        )
                    }
                }
            }
        }
    }
}

fun List<WeeklyDay>.dailyAverage(): Int {
    return this.sumOf { it.dailyStat.steps } / 7
}

@Preview
@Composable
fun DailyAverageCardPreview() {
    SmartStepTheme {
        DailyAverageCard(
            days = listOf(
                WeeklyDay(
                    dailyStat = DailyStat(
                        epochDay = 0L,
                        steps = 100,
                        kms = 1.2,
                        kcal = 100.1,
                        minutes = 2.2
                    ),
                    dayName = "Sun",
                    isToday = false
                ),

                WeeklyDay(
                    dailyStat = DailyStat(
                        epochDay = 0L,
                        steps = 100,
                        kms = 1.2,
                        kcal = 100.1,
                        minutes = 2.2
                    ),
                    dayName = "Mon",
                    isToday = false
                ),

                WeeklyDay(
                    dailyStat = DailyStat(
                        epochDay = 0L,
                        steps = 100,
                        kms = 1.2,
                        kcal = 100.1,
                        minutes = 2.2
                    ),
                    dayName = "Tue",
                    isToday = false
                ),

                WeeklyDay(
                    dailyStat = DailyStat(
                        epochDay = 0L,
                        steps = 100,
                        kms = 1.2,
                        kcal = 100.1,
                        minutes = 2.2
                    ),
                    dayName = "Wed",
                    isToday = false
                ),

                WeeklyDay(
                    dailyStat = DailyStat(
                        epochDay = 0L,
                        steps = 100,
                        kms = 1.2,
                        kcal = 100.1,
                        minutes = 2.2
                    ),
                    dayName = "Thu",
                    isToday = false
                ),

                WeeklyDay(
                    dailyStat = DailyStat(
                        epochDay = 0L,
                        steps = 100,
                        kms = 1.2,
                        kcal = 100.1,
                        minutes = 2.2
                    ),
                    dayName = "Fri",
                    isToday = false
                ),

                WeeklyDay(
                    dailyStat = DailyStat(
                        epochDay = 0L,
                        steps = 100,
                        kms = 1.2,
                        kcal = 100.1,
                        minutes = 2.2
                    ),
                    dayName = "Sat",
                    isToday = false
                ),
            ),
            goalSteps = 5000
        )
    }
}