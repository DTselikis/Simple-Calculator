package com.example.simplecalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.R
import com.example.simplecalculator.data.local.LocalCurrenciesDataSource
import com.example.simplecalculator.domain.model.CurrencyInfo
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesMenu(
    currencies: List<CurrencyInfo>,
    selectedCurrency: CurrencyInfo?,
    convertedResult: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onCurrencyChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        TextButton(
            onClick = { onExpandedChange(!expanded) },
            modifier = Modifier
                .clip(CircleShape)
                .background(backgroundColor)
                .menuAnchor()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedCurrency?.let {
                        stringResource(
                            id = R.string.converted_result,
                            it.shortCode,
                            convertedResult,
                            it.symbol
                        )
                    } ?: ""
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
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
                convertedResult = "4.4",
                expanded = false,
                onExpandedChange = {},
                onCurrencyChange = {}
            )
        }
    }
}

@Preview
@Composable
fun CurrenciesMenuExpandedPreview() {
    SimpleCalculatorTheme {
        Surface {
            val currencies = LocalCurrenciesDataSource.availableCurrencies
            CurrenciesMenu(
                currencies = currencies,
                selectedCurrency = currencies[0],
                convertedResult = "4.4",
                expanded = true,
                onExpandedChange = {},
                onCurrencyChange = {}
            )
        }
    }
}
