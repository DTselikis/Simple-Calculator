package com.example.simplecalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.consts.EUR0_SHORT_CODE
import com.example.simplecalculator.di.IoDispatcher
import com.example.simplecalculator.domain.use_case.ConvertToCurrencyUseCase
import com.example.simplecalculator.domain.use_case.ExpressionCalculator
import com.example.simplecalculator.domain.use_case.ExpressionSanitization
import com.example.simplecalculator.domain.use_case.GetAvailableCurrenciesUseCase
import com.example.simplecalculator.helper.ErrorResolver
import com.example.simplecalculator.helper.Resource
import com.example.simplecalculator.utils.endsWithDigit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SimpleCalculatorViewModel @Inject constructor(
    private val expressionCalculator: ExpressionCalculator,
    private val expressionSanitization: ExpressionSanitization,
    private val getAvailableCurrenciesUseCase: GetAvailableCurrenciesUseCase,
    private val convertToCurrencyUseCase: ConvertToCurrencyUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineContext
) : ViewModel() {


    private var convertToCurrencyJob: Job? = null

    var uiState by mutableStateOf(SimpleCalculatorUiState())
        private set

    init {
        uiState = SimpleCalculatorUiState()
        fetchAvailableCurrencies()
    }

    private fun fetchAvailableCurrencies() {
        viewModelScope.launch {
            var tries = 10

            while (tries > 0 && uiState.currencyConverterUiState.availableCurrencies.isEmpty()) {
                getAvailableCurrenciesUseCase.getAvailableCurrencies().let { result ->
                    if (result is Resource.Success) {
                        uiState = uiState.copy(
                            currencyConverterUiState = uiState.currencyConverterUiState.copy(
                                availableCurrencies = result.data!!,
                                selectedCurrency = result.data.find {
                                    it.shortCode == EUR0_SHORT_CODE
                                }
                            )
                        )
                    } else {
                        delay(5000L)
                        tries -= 1
                    }
                }
            }

            if (uiState.currencyConverterUiState.availableCurrencies.isEmpty()) {
                uiState = uiState.copy(errorMessage = ErrorResolver.errorCodeToMessage[ErrorResolver.CURRENCIES_NOT_FETCHED])
            }
        }
    }

    fun onEvent(event: CalculatorAction) {
        when (event) {
            CalculatorAction.Calculate -> calculate(eraseInput = true)
            CalculatorAction.Clear -> clear()
            CalculatorAction.Delete -> delete()
            is CalculatorAction.Append -> append(event.symbol)
            is CalculatorAction.CurrencyChanged -> convertToCurrency(event.id)
            is CalculatorAction.ExpandedChanged -> changeExpandState(event.expanded)
        }
    }

    private fun calculate(eraseInput: Boolean = false) {
        val result = expressionCalculator.calculate(uiState.input)

        uiState.currencyConverterUiState.selectedCurrency?.let { currencyInfo ->
            convertToCurrency(currencyInfo.id)
        }

        uiState = uiState.copy(
            input = if (eraseInput) result.toString() else uiState.input,
            result = result.toString()
        )
    }

    private fun append(symbol: Char) {
        val expression = expressionSanitization.sanitize(uiState.input, symbol)
        uiState = uiState.copy(input = expression)

        if (uiState.input.endsWithDigit()) {
            calculate()
        }
    }

    private fun clear() {
        uiState = SimpleCalculatorUiState(
            currencyConverterUiState = uiState.currencyConverterUiState.copy(convertedResult = "")
        )
    }

    private fun delete() {
        if (uiState.input.length > 1) {
            uiState = uiState.copy(input = uiState.input.dropLast(1))

            if (uiState.input.endsWithDigit()) {
                calculate()
            }
        } else {
            clear()
        }
    }

    private fun convertToCurrency(id: Int) {
        convertToCurrencyJob?.cancel()
        uiState.currencyConverterUiState.availableCurrencies.find { it.id == id }
            ?.let { currencyInfo ->
                convertToCurrencyJob = viewModelScope.launch(ioDispatcher) {
                    val result = convertToCurrencyUseCase.convertToCurrency(
                        from = EUR0_SHORT_CODE,
                        to = currencyInfo.shortCode,
                        amount = uiState.result
                    )

                    uiState = uiState.copy(
                        currencyConverterUiState = uiState.currencyConverterUiState.copy(
                            convertedResult = result.data?.value?.toString() ?: "",
                            selectedCurrency = currencyInfo
                        ),
                        errorMessage = ErrorResolver.errorCodeToMessage[result.message]
                    )
                }
            }
    }

    private fun changeExpandState(expanded: Boolean) {
        uiState = uiState.copy(
            currencyConverterUiState = uiState.currencyConverterUiState.copy(expanded = expanded)
        )
    }
}
