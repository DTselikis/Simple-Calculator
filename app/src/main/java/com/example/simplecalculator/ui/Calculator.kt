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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

val buttons = listOf(
    listOf(
        Button(symbol = "AC", action = CalculatorAction.Clear, aspectRatio = 2F, weight = 2F),
        Button(symbol = "DEL", action = CalculatorAction.Delete),
        Button(symbol = "/", action = CalculatorAction.Append('/'))
    ),
    listOf(
        Button(symbol = "7", action = CalculatorAction.Append('7')),
        Button(symbol = "8", action = CalculatorAction.Append('8')),
        Button(symbol = "9", action = CalculatorAction.Append('9')),
        Button(symbol = "X", action = CalculatorAction.Append('X'))
    ),
    listOf(
        Button(symbol = "4", action = CalculatorAction.Append('4')),
        Button(symbol = "5", action = CalculatorAction.Append('5')),
        Button(symbol = "6", action = CalculatorAction.Append('6')),
        Button(symbol = "-", action = CalculatorAction.Append('-'))
    ),
    listOf(
        Button(symbol = "1", action = CalculatorAction.Append('1')),
        Button(symbol = "2", action = CalculatorAction.Append('2')),
        Button(symbol = "3", action = CalculatorAction.Append('3')),
        Button(symbol = "+", action = CalculatorAction.Append('+'))
    ),
    listOf(
        Button(symbol = "0", action = CalculatorAction.Append('0'), aspectRatio = 2F, weight = 2F),
        Button(symbol = ".", action = CalculatorAction.Append('.')),
        Button(symbol = "=", action = CalculatorAction.Calculate)
    )
)

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    viewModel: SimpleCalculatorViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    CalculatorContent(
        uiState = uiState,
        onClick = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
fun CalculatorContent(
    uiState: SimpleCalculatorUiState,
    modifier: Modifier = Modifier,
    onClick: (CalculatorAction) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .weight(1F)
            .fillMaxWidth()
        ) {
            Text(
                text = uiState.input,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
        buttons.forEach { buttonsList ->
            ButtonsRow(
                buttons = buttonsList,
                buttonSpacing = 12.dp,
                onClick = onClick
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorContentPreview() {
    SimpleCalculatorTheme {
        Surface {
            CalculatorContent(
                uiState = SimpleCalculatorUiState(),
                onClick = { }
            )
        }
    }
}