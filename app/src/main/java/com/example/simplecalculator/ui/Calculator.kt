package com.example.simplecalculator.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.data.local.LocalCurrenciesDataSource
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@Composable
fun Calculator(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: SimpleCalculatorViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    CalculatorContent(
        windowSizeClass = windowSizeClass,
        uiState = uiState,
        onClick = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
fun CalculatorContent(
    windowSizeClass: WindowSizeClass,
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
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                CompactLayout(
                    input = uiState.input,
                    result = uiState.result,
                    onClick = onClick
                )
            } else {
                Spacer(modifier = Modifier.height(10.dp))
                MediumLayout(
                    input = uiState.input,
                    result = uiState.result,
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(showSystemUi = true)
@Composable
fun CalculatorContentPreview() {
    SimpleCalculatorTheme {
        Surface {
            val currencies = LocalCurrenciesDataSource.availableCurrencies
            CalculatorContent(
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(400.dp, 470.dp)),
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showSystemUi = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun CalculatorContentMediumPreview() {
    SimpleCalculatorTheme {
        Surface {
            val currencies = LocalCurrenciesDataSource.availableCurrencies
            CalculatorContent(
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(600.dp, 500.dp)),
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
