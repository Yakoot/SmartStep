package dev.mamkin.smartstep.feature.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.smartstep.core.presentation.theme.AppTheme
import dev.mamkin.smartstep.core.presentation.theme.SmartStepTheme
import org.jetbrains.compose.resources.painterResource
import smartstep.composeapp.generated.resources.Res
import smartstep.composeapp.generated.resources.selected_icon

@Composable
fun DropdownItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    val backgroundColor = if (isSelected)
        AppTheme.colors.backgroundSecondary
    else
        AppTheme.colors.backgroundWhite

    DropdownMenuItem(
        modifier = modifier
            .height(48.dp)
            .background(backgroundColor, RoundedCornerShape(10.dp))
        ,
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = AppTheme.colors.textPrimary
            )
        },
        colors = MenuItemColors(
            textColor = AppTheme.colors.textPrimary,
            leadingIconColor =  AppTheme.colors.buttonPrimary,
            trailingIconColor = AppTheme.colors.buttonPrimary,
            disabledTextColor = AppTheme.colors.textPrimary,
            disabledLeadingIconColor = AppTheme.colors.buttonPrimary,
            disabledTrailingIconColor =  AppTheme.colors.buttonPrimary
        ),
        trailingIcon = if (isSelected) {
            {
                Icon(
                    painter = painterResource(Res.drawable.selected_icon),
                    contentDescription = text,
                    tint = AppTheme.colors.buttonPrimary
                )
            }
        } else null,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp),
        onClick = onClick
    )
}

@Preview
@Composable
fun DropdownItemPreview() {
    SmartStepTheme {
        DropdownItem(
            text = "Female",
            isSelected = true,
            onClick = {}
        )
    }
}
