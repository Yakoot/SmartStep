package dev.mamkin.smartstep.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseSkeleton(
    title: @Composable () -> Unit,
    body: @Composable () -> Unit,
    footer: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        title()

        Spacer(Modifier.height(16.dp))

        body()

        Spacer(Modifier.height(24.dp))

        footer()
    }
}