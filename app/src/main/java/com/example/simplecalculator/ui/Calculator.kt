package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.R
import com.example.simplecalculator.data.local.LocalCurrenciesDataSource
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
    val snackBarHostState = remember { SnackbarHostState() }
    val errorMessage = uiState.errorMessage?.let { stringResource(id = it) } ?: ""
    LaunchedEffect(key1 = uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackBarHostState.showErrorSnackBar(
                message = errorMessage,
                onClick
            )
        }
    }
    
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            CurrencyConverter(
                currencyConverterUiState = uiState.currencyConverterUiState,
                onCurrencyChange = onClick,
                onExpandedChange = onClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Box(modifier = Modifier
                .weight(2F)
                .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Text(
                        text = uiState.input,
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (uiState.result.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.result, uiState.result),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
            buttons.forEach { buttonsList ->
                ButtonsRow(
                    buttons = buttonsList,
                    onClick = onClick
                )
            }
        }

    }
}

suspend fun SnackbarHostState.showErrorSnackBar(
    message: String,
    onDismiss: (CalculatorAction) -> Unit
    ) {
    showSnackbar(
        message = message,
        withDismissAction = true
    ).let { snackBarResult ->
        when (snackBarResult) {
            SnackbarResult.Dismissed, -> onDismiss(CalculatorAction.ClearError)
            SnackbarResult.ActionPerformed -> onDismiss(CalculatorAction.ClearError)
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorContentPreview() {
    SimpleCalculatorTheme {
        Surface {
            val currencies = LocalCurrenciesDataSource.availableCurrencies
            CalculatorContent(
                uiState = SimpleCalculatorUiState(
                    input = "2+2",
                    result = "4",
                    currencyConverterUiState = CurrencyConverterUiState(
                        availableCurrencies = currencies,
                        selectedCurrency = currencies[0],
                        convertedResult = "22",
                        expanded = false
                    )
                ),
                onClick = { }
            )
        }
    }
}