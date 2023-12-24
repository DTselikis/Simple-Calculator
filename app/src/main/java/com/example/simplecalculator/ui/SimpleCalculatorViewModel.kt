package com.example.simplecalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.simplecalculator.CalculatorAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimpleCalculatorViewModel @Inject constructor(): ViewModel() {

    var uiState by mutableStateOf(SimpleCalculatorUiState())
        private set

    fun onEvent(event: CalculatorAction) {
        when (event) {
            CalculatorAction.Calculate -> TODO()
            CalculatorAction.Clear -> clear()
            CalculatorAction.Delete -> delete()
            is CalculatorAction.Append -> append(event.symbol)
        }
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