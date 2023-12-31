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
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.data.local.LocalCurrenciesDataSource
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@Composable
fun CurrencyConverter(
    currencyConverterUiState: CurrencyConverterUiState,
    onCurrencyChange: (CalculatorAction) -> Unit,
    onExpandedChange: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box {
            CurrenciesMenu(
                currencies = currencyConverterUiState.availableCurrencies,
                selectedCurrency = currencyConverterUiState.selectedCurrency,
                expanded = currencyConverterUiState.expanded,
                onExpandedChange = { expanded ->
                    onExpandedChange(CalculatorAction.ExpandedChanged(expanded))
                },
                onCurrencyChange = { id ->
                    onCurrencyChange(CalculatorAction.CurrencyChanged(id))
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
        }
        Box {
            Text(
                text = currencyConverterUiState.convertedResult,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview
@Composable
fun CurrencyConverterPreview() {
    SimpleCalculatorTheme {
        Surface {
            val currencies = LocalCurrenciesDataSource.availableCurrencies
            val currencyConverterUiState = CurrencyConverterUiState(
                availableCurrencies = currencies,
                selectedCurrency = currencies[0],
                convertedResult = "22",
                expanded = false
            )
            CurrencyConverter(
                currencyConverterUiState = currencyConverterUiState,
                onCurrencyChange = {},
                onExpandedChange = {}
            )
        }
    }
}