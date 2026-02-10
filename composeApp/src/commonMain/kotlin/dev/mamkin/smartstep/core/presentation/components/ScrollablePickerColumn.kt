package dev.mamkin.smartstep.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import kotlin.math.abs

@Composable
fun ScrollablePickerColumn(
    value: String,
    values: List<String>,
    selectedValueUnitText: String? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    var isProgrammaticScroll by remember { mutableStateOf(false) }

    val snapBehavior = rememberSnapFlingBehavior(
        lazyListState = listState,
        snapPosition = SnapPosition.Start
    )
    val itemHeight = 44.dp
    val itemsCount = 4
    val itemsBefore = 2
    val itemsAfter = 1

    LaunchedEffect(value, values) {
        val selectedIndex = values.indexOf(value).takeIf { it >= 0 } ?: 0
        isProgrammaticScroll = true
        listState.animateScrollToItem(selectedIndex)
        isProgrammaticScroll = false
    }

    LaunchedEffect(listState.isScrollInProgress) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (!listState.isScrollInProgress && !isProgrammaticScroll && index in values.indices) {
                    onValueChange(values[index])
                }
            }
    }
    Box(modifier = modifier.height(itemsCount * itemHeight)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .align(Alignment.TopStart)
                .offset(x = 0.dp, y = itemHeight * 2)
                .background(AppTheme.colors.backgroundTertiary)
        ) {
            if (selectedValueUnitText != null) {
                Row(
                    modifier = Modifier.matchParentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.width(44.dp))
                    Text (
                        text = selectedValueUnitText,
                        style = MaterialTheme.typography.titleMedium,
                        color = AppTheme.colors.textPrimary
                    )
                }

            }
        }
        LazyColumn(
            state = listState,
            flingBehavior = snapBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemsBefore) {
                Spacer(modifier = Modifier.height(itemHeight))
            }

            items(values) { rowValue ->
                val textColor = if (value == rowValue) {
                    AppTheme.colors.textPrimary
                } else {
                    AppTheme.colors.textSecondary
                }
                if (selectedValueUnitText != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = rowValue,
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor,
                            modifier = Modifier
                                .height(itemHeight)
                                .wrapContentHeight(Alignment.CenterVertically),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(44.dp))
                    }
                } else {
                    Text(
                        text = rowValue,
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        modifier = Modifier
                            .height(itemHeight)
                            .wrapContentHeight(Alignment.CenterVertically),
                        textAlign = TextAlign.Center
                    )
                }

            }

            items(itemsAfter) {
                Spacer(modifier = Modifier.height(itemHeight))
            }
        }
    }
}

@Preview
@Composable
fun ScrollablePickerColumnPreview() {
    var selectedValue by mutableStateOf("1")
    SmartStepTheme {
        Column {
            Button(onClick = {selectedValue = "5"}) {
                Text(
                    text = "scroll to 5"
                )
            }
            ScrollablePickerColumn(
                value = selectedValue,
                values = (1..100).toList().map { it.toString() },
                onValueChange = {selectedValue = it},
                selectedValueUnitText = "cm"
            )
        }


    }
}