package com.example.simplecalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.domain.use_case.ExpressionCalculator
import com.example.simplecalculator.domain.use_case.ExpressionSanitization
import com.example.simplecalculator.utils.notEndsWithZero
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimpleCalculatorViewModel @Inject constructor(
    private val expressionCalculator: ExpressionCalculator,
    private val expressionSanitization: ExpressionSanitization
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

    fun append(symbol: Char) {
        val expression = expressionSanitization.sanitize(uiState.input, symbol)
        uiState = uiState.copy(input = expression)
    }

    fun clear() {
        uiState = SimpleCalculatorUiState()
    }

    fun delete() {
        if (uiState.input.length > 1) {
            uiState = uiState.copy(input = uiState.input.dropLast(1))
        } else if (uiState.input.notEndsWithZero()) {
            uiState = uiState.copy(input = "0")
        }
    }
}