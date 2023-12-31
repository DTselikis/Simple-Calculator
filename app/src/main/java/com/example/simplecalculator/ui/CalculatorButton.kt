package com.example.simplecalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

data class Button(
    val symbol: String,
    val action: CalculatorAction,
    val aspectRatio: Float = 1F,
    val weight: Float = 1F
)

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    buttonTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Text(
            text = symbol,
            color = textColor,
            style = buttonTextStyle
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