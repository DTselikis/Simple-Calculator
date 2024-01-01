package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.ui.theme.AppTheme
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.utils.endsWithOperationSymbolOrDecimalPoint

@Composable
fun ButtonsRow(
    buttons: List<Button>,
    modifier: Modifier = Modifier,
    buttonHorizontalSpacing: Dp = AppTheme.dimens.buttonHorizontalSpacing,
    buttonVerticalSpacing: Dp = AppTheme.dimens.buttonVerticalSpacing,
    buttonTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: (CalculatorAction) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(buttonHorizontalSpacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = buttonVerticalSpacing)
    ) {
        buttons.forEach { button ->
            val (backgroundColor, textColor) = if (button.symbol.endsWithOperationSymbolOrDecimalPoint())
                Pair(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer)
            else
                Pair(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
            CalculatorButton(
                symbol = button.symbol,
                backgroundColor = backgroundColor,
                textColor = textColor,
                buttonTextStyle = buttonTextStyle,
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
                buttons = compactLayoutButtons[1],
                buttonHorizontalSpacing = 12.dp
            ) { }
        }
    }
}