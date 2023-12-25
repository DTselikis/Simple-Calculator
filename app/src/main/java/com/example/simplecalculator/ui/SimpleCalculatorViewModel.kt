package com.example.simplecalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.domain.use_case.ExpressionCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimpleCalculatorViewModel @Inject constructor(
    private val expressionCalculator: ExpressionCalculator
): ViewModel() {

    var uiState by mutableStateOf(SimpleCalculatorUiState())
        private set

    fun onEvent(event: CalculatorAction) {
        when (event) {
            CalculatorAction.Calculate -> calculate()
            CalculatorAction.Clear -> clear()
            CalculatorAction.Delete -> delete()
            is CalculatorAction.Append -> append(event.symbol)
        }
    }

    fun calculate() {
        val result = expressionCalculator.calculate(uiState.input)

        uiState = uiState.copy(input = result.toString())
    }

    fun append(symbol: String) {
        uiState = uiState.copy(input = uiState.input + symbol)
    }

    fun clear() {
        uiState = SimpleCalculatorUiState()
    }

    fun delete() {
        uiState = uiState.copy(input = uiState.input.dropLast(1))
    }
}