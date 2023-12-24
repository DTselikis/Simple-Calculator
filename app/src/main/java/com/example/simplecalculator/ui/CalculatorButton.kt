package com.example.simplecalculator.ui

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

data class Button(
    val symbol: String,
    val aspectRatio: Float = 1F,
    val weight: Float = 1F,
    val onClick: () -> Unit
)

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        shape = CircleShape,
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = symbol
        )
    }
}

@Preview
@Composable
fun CalculatorButtonPreview() {
    SimpleCalculatorTheme {
        Surface {
            CalculatorButton(
                symbol = "1"
            ) { }
        }
    }
}