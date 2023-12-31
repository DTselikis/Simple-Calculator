package com.example.simplecalculator.ui

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecalculator.R
import com.example.simplecalculator.data.local.LocalCurrenciesDataSource
import com.example.simplecalculator.domain.model.CurrencyInfo
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesMenu(
    currencies: List<CurrencyInfo>,
    selectedCurrency: CurrencyInfo?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onCurrencyChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        TextField(
            value = selectedCurrency?.let { currencyInfo ->
                stringResource(
                    id = R.string.currency_name,
                    currencyInfo.shortCode,
                    currencyInfo.symbol
                )
            } ?: "",
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            currencies.forEach { currencyInfo ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(
                                id = R.string.currency_name,
                                currencyInfo.shortCode,
                                currencyInfo.symbol
                            )
                        )
                    },
                    onClick = {
                        onCurrencyChange(currencyInfo.id)
                        onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview
@Composable
fun CurrenciesMenuPreview() {
    SimpleCalculatorTheme {
        Surface {
            val currencies = LocalCurrenciesDataSource.availableCurrencies
            CurrenciesMenu(
                currencies = currencies,
                selectedCurrency = currencies[0],
                expanded = false,
                onExpandedChange = {},
                onCurrencyChange = {}
            )
        }
    }
}
