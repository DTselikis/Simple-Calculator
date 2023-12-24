package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@Composable
fun ButtonsRow(
    buttons: List<Button>,
    buttonSpacing: Dp,
    modifier: Modifier = Modifier,
    onClick: (CalculatorAction) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        buttons.forEach { button ->
            CalculatorButton(
                symbol = button.symbol,
                modifier = Modifier
                    .aspectRatio(button.aspectRatio)
                    .weight(button.weight)
            ) {
                onClick(button.action)
            }
        }
    }
}

@Preview
@Composable
fun ButtonsRowPreview() {
    SimpleCalculatorTheme {
        Surface {
            ButtonsRow(
                buttons = buttons[1],
                buttonSpacing = 12.dp
            ) { }
        }
    }
}