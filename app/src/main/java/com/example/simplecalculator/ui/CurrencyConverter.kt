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
import com.example.simplecalculator.domain.model.CurrencyInfo
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
        currencyConverterUiState.selectedCurrencyId?.let {
            Box {
                CurrenciesMenu(
                    currencies = currencyConverterUiState.availableCurrencies,
                    selectedCurrencyId = currencyConverterUiState.selectedCurrencyId,
                    expanded = currencyConverterUiState.expanded,
                    onExpandedChange = { expanded ->
                        onExpandedChange(CalculatorAction.ExpandedChanged(expanded))
                    },
                    onCurrencyChange = { shortCode ->
                        onCurrencyChange(CalculatorAction.CurrencyChanged(shortCode))
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                )
            }
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
            val currencies = listOf(
                CurrencyInfo(
                    id = 46,
                    name = "Euro",
                    shortCode = "EUR",
                    symbol = "€"
                ),
                CurrencyInfo(
                    id = 8,
                    name = "Australian Dollar",
                    shortCode = "AUD",
                    symbol = "\$"
                ),
                CurrencyInfo(
                    id = 8,
                    name = "US Dollar",
                    shortCode = "USD",
                    symbol = "\$"
                )
            )
            val currencyConverterUiState = CurrencyConverterUiState(
                availableCurrencies = currencies,
                selectedCurrencyId = 46,
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