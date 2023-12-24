package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

val buttons = listOf(
    listOf(
        Button(symbol = "AC", aspectRatio = 2F, weight = 2F) {},
        Button(symbol = "DEL") {},
        Button(symbol = "/") {},
    ),
    listOf(
        Button(symbol = "7") {},
        Button(symbol = "8") {},
        Button(symbol = "9") {},
        Button(symbol = "X") {},
    ),
    listOf(
        Button(symbol = "4") {},
        Button(symbol = "5") {},
        Button(symbol = "6") {},
        Button(symbol = "-") {},
    ),
    listOf(
        Button(symbol = "1") {},
        Button(symbol = "2") {},
        Button(symbol = "3") {},
        Button(symbol = "+") {},
    ),
    listOf(
        Button(symbol = "0", aspectRatio = 2F, weight = 2F) {},
        Button(symbol = ".") {},
        Button(symbol = "=") {},
    )
)

@Composable
fun Calculator(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .weight(1F)
            .fillMaxWidth()
        ) {
            Text(
                text = "0",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
        buttons.forEach { buttonsList ->
            ButtonsRow(buttons = buttonsList, buttonSpacing = 12.dp)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorPreview() {
    SimpleCalculatorTheme {
        Surface {
            Calculator()
        }
    }
}