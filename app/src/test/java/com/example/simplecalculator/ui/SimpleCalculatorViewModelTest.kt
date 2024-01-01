package com.example.simplecalculator.ui

import com.example.simplecalculator.CalculatorAction
import com.example.simplecalculator.data.local.LocalCurrenciesDataSource
import com.example.simplecalculator.domain.model.Currency
import com.example.simplecalculator.domain.use_case.CalculateExpressionUseCase
import com.example.simplecalculator.domain.use_case.ConvertToCurrencyUseCase
import com.example.simplecalculator.domain.use_case.GetAvailableCurrenciesUseCase
import com.example.simplecalculator.domain.use_case.SanitizeExpressionUseCase
import com.example.simplecalculator.helper.ErrorResolver
import com.example.simplecalculator.helper.Resource
import com.example.simplecalculator.rule.MainDispatcherRule
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import io.mockk.verifyAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SimpleCalculatorViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var calculateExpressionUseCase: CalculateExpressionUseCase

    @MockK
    private lateinit var sanitizeExpressionUseCase: SanitizeExpressionUseCase

    @MockK
    private lateinit var getAvailableCurrenciesUseCase: GetAvailableCurrenciesUseCase

    @MockK
    private lateinit var convertToCurrencyUseCase: ConvertToCurrencyUseCase

    private lateinit var viewModel: SimpleCalculatorViewModel

    @Before
    fun before() = runTest {
        coEvery { getAvailableCurrenciesUseCase.getAvailableCurrencies() } returns Resource.Success(
            data = LocalCurrenciesDataSource.availableCurrencies
        )

        viewModel = SimpleCalculatorViewModel(
            calculateExpressionUseCase,
            sanitizeExpressionUseCase,
            getAvailableCurrenciesUseCase,
            convertToCurrencyUseCase,
            UnconfinedTestDispatcher(testScheduler)
        )
    }

    @Test
    fun `fetch available currencies failed`() = runTest {
        coEvery { getAvailableCurrenciesUseCase.getAvailableCurrencies() } returns
                Resource.Error(message = ErrorResolver.CURRENCIES_NOT_FETCHED)

        viewModel = SimpleCalculatorViewModel(
            calculateExpressionUseCase,
            sanitizeExpressionUseCase,
            getAvailableCurrenciesUseCase,
            convertToCurrencyUseCase,
            StandardTestDispatcher(testScheduler)
        )
        advanceUntilIdle()

        coVerify(exactly = 11) { getAvailableCurrenciesUseCase.getAvailableCurrencies() }
        Assert.assertEquals(
            ErrorResolver.errorCodeToMessage[ErrorResolver.CURRENCIES_NOT_FETCHED],
            viewModel.uiState.errorMessage
        )
    }

    @Test
    fun `fetch available currencies was successful`() = runTest {
        coEvery { getAvailableCurrenciesUseCase.getAvailableCurrencies() } returns Resource.Success(
            data = LocalCurrenciesDataSource.availableCurrencies
        )

        viewModel = SimpleCalculatorViewModel(
            calculateExpressionUseCase,
            sanitizeExpressionUseCase,
            getAvailableCurrenciesUseCase,
            convertToCurrencyUseCase,
            StandardTestDispatcher(testScheduler)
        )
        advanceUntilIdle()

        coVerify { getAvailableCurrenciesUseCase.getAvailableCurrencies() }
        Assert.assertNull(viewModel.uiState.errorMessage)
        Assert.assertEquals(
            LocalCurrenciesDataSource.availableCurrencies,
            viewModel.uiState.currencyConverterUiState.availableCurrencies
        )
    }

    @Test
    fun `calculate with erase input`() {
        every { sanitizeExpressionUseCase.sanitize(any(), any()) } returnsMany listOf("0+", "0+5")
        every { calculateExpressionUseCase.calculate("0+5") } returns 5.0
        every { calculateExpressionUseCase.calculate("0") } returns 0.0
        mockConvertToCurrency("0")
        mockConvertToCurrency("5.0")

        viewModel.onEvent(CalculatorAction.Append('+'))
        viewModel.onEvent(CalculatorAction.Append('5'))
        viewModel.onEvent(CalculatorAction.Calculate)

        Assert.assertEquals("5.0", viewModel.uiState.result)
        Assert.assertEquals("5.0", viewModel.uiState.input)
    }

    @Test
    fun `calculate without erase input`() {
        every { sanitizeExpressionUseCase.sanitize(any(), any()) } returnsMany listOf("0+", "0+5")
        every { calculateExpressionUseCase.calculate("0+5") } returns 5.0
        every { calculateExpressionUseCase.calculate("0") } returns 0.0
        mockConvertToCurrency("0")
        mockConvertToCurrency("5.0")

        viewModel.onEvent(CalculatorAction.Append('+'))
        viewModel.onEvent(CalculatorAction.Append('5'))

        Assert.assertEquals("5.0", viewModel.uiState.result)
        Assert.assertEquals("0+5", viewModel.uiState.input)
    }

    @Test
    fun `append anything`() {
        every { sanitizeExpressionUseCase.sanitize(any(), any()) } returnsMany listOf(
            "0+",
            "0+2",
            "0+2.",
            "0+2.5"
        )
        every { calculateExpressionUseCase.calculate("0+2") } returns 2.0
        every { calculateExpressionUseCase.calculate("0+2.5") } returns 2.5
        mockConvertToCurrency("0")
        mockConvertToCurrency("2.0")
        mockConvertToCurrency("2.5")

        viewModel.onEvent(CalculatorAction.Append('+'))
        viewModel.onEvent(CalculatorAction.Append('2'))
        viewModel.onEvent(CalculatorAction.Append('.'))
        viewModel.onEvent(CalculatorAction.Append('5'))

        verifyAll {
            calculateExpressionUseCase.calculate("0+2")
            calculateExpressionUseCase.calculate("0+2.5")
            sanitizeExpressionUseCase.sanitize("0", '+')
            sanitizeExpressionUseCase.sanitize("0+", '2')
            sanitizeExpressionUseCase.sanitize("0+2", '.')
            sanitizeExpressionUseCase.sanitize("0+2.", '5')
        }
        Assert.assertEquals("0+2.5", viewModel.uiState.input)
        Assert.assertEquals("2.5", viewModel.uiState.result)
        Assert.assertEquals("2.0", viewModel.uiState.currencyConverterUiState.convertedResult)
    }

    @Test
    fun `clear results in default state`() {
        every { sanitizeExpressionUseCase.sanitize(any(), any()) } returnsMany listOf(
            "0+",
            "0+5",
            "0"
        )
        every { calculateExpressionUseCase.calculate("0+5") } returns 5.0
        every { calculateExpressionUseCase.calculate("0") } returns 0.0
        mockConvertToCurrency("0")
        mockConvertToCurrency("5.0")

        viewModel.onEvent(CalculatorAction.ExpandedChanged(true))
        viewModel.onEvent(CalculatorAction.CurrencyChanged(8))
        viewModel.onEvent(CalculatorAction.Append('+'))
        viewModel.onEvent(CalculatorAction.Append('5'))
        viewModel.onEvent(CalculatorAction.Delete)
        viewModel.onEvent(CalculatorAction.Delete)
        viewModel.onEvent(CalculatorAction.Clear)

        Assert.assertEquals("0", viewModel.uiState.input)
        Assert.assertEquals("0", viewModel.uiState.result)
        Assert.assertTrue(viewModel.uiState.currencyConverterUiState.expanded)
        Assert.assertEquals(
            LocalCurrenciesDataSource.availableCurrencies[1],
            viewModel.uiState.currencyConverterUiState.selectedCurrency
        )
        Assert.assertEquals("", viewModel.uiState.currencyConverterUiState.convertedResult)
    }

    @Test
    fun `change to unknown currency does nothing`() {
        mockConvertToCurrency("0")

        viewModel.onEvent(CalculatorAction.CurrencyChanged(-2))

        verify { convertToCurrencyUseCase wasNot Called }
        Assert.assertEquals(
            LocalCurrenciesDataSource.availableCurrencies[2],
            viewModel.uiState.currencyConverterUiState.selectedCurrency
        )
        Assert.assertEquals("", viewModel.uiState.currencyConverterUiState.convertedResult)
    }

    @Test
    fun `change currency result to convert to new currency`() {
        mockConvertToCurrency("0")

        viewModel.onEvent(CalculatorAction.CurrencyChanged(LocalCurrenciesDataSource.availableCurrencies[1].id))

        coVerify {
            convertToCurrencyUseCase.convertToCurrency(
                from = "EUR",
                to = LocalCurrenciesDataSource.availableCurrencies[1].shortCode,
                amount = "0"
            )
        }
        Assert.assertEquals(
            LocalCurrenciesDataSource.availableCurrencies[1],
            viewModel.uiState.currencyConverterUiState.selectedCurrency
        )
        Assert.assertEquals("0.0", viewModel.uiState.currencyConverterUiState.convertedResult)
    }

    @Test
    fun `delete three times from input having length three results in default state`() {
        every { sanitizeExpressionUseCase.sanitize(any(), any()) } returnsMany listOf(
            "0+",
            "0+5",
            "0"
        )
        every { calculateExpressionUseCase.calculate("0+5") } returns 5.0
        every { calculateExpressionUseCase.calculate("0") } returns 0.0
        mockConvertToCurrency("0")
        mockConvertToCurrency("5.0")

        viewModel.onEvent(CalculatorAction.Append('+'))
        viewModel.onEvent(CalculatorAction.Append('5'))
        viewModel.onEvent(CalculatorAction.Delete)
        viewModel.onEvent(CalculatorAction.Delete)
        viewModel.onEvent(CalculatorAction.Delete)

        Assert.assertEquals("0", viewModel.uiState.input)
        Assert.assertEquals("", viewModel.uiState.currencyConverterUiState.convertedResult)
    }

    @Test
    fun `delete from input ending with symbol having length more than two results in length two`() {
        every { sanitizeExpressionUseCase.sanitize(any(), any()) } returnsMany listOf("0+", "0+5")
        every { calculateExpressionUseCase.calculate("0+5") } returns "5".toDouble()
        mockConvertToCurrency("0")

        viewModel.onEvent(CalculatorAction.Append('+'))
        viewModel.onEvent(CalculatorAction.Append('5'))
        viewModel.onEvent(CalculatorAction.Delete)

        Assert.assertEquals("0+", viewModel.uiState.input)
    }

    @Test
    fun `delete from input ending with symbol having length two results in default state`() {
        every { calculateExpressionUseCase.calculate(viewModel.uiState.input) } returns viewModel.uiState.input.toDouble()
        mockConvertToCurrency(viewModel.uiState.input)
        every {
            sanitizeExpressionUseCase.sanitize(
                viewModel.uiState.input,
                '+'
            )
        } returns viewModel.uiState.input + "+"

        viewModel.onEvent(CalculatorAction.Append('+'))
        viewModel.onEvent(CalculatorAction.Delete)

        Assert.assertEquals("0", viewModel.uiState.input)
    }

    @Test
    fun `change expand state, state changed`() {
        Assert.assertFalse(viewModel.uiState.currencyConverterUiState.expanded)
        viewModel.onEvent(CalculatorAction.ExpandedChanged(true))
        Assert.assertTrue(viewModel.uiState.currencyConverterUiState.expanded)
    }

    @Test
    fun `clear error message`() = runTest {
        coEvery { getAvailableCurrenciesUseCase.getAvailableCurrencies() } returns
                Resource.Error(message = ErrorResolver.CURRENCIES_NOT_FETCHED)

        viewModel = SimpleCalculatorViewModel(
            calculateExpressionUseCase,
            sanitizeExpressionUseCase,
            getAvailableCurrenciesUseCase,
            convertToCurrencyUseCase,
            StandardTestDispatcher(testScheduler)
        )
        advanceUntilIdle()

        Assert.assertEquals(
            ErrorResolver.errorCodeToMessage[ErrorResolver.CURRENCIES_NOT_FETCHED],
            viewModel.uiState.errorMessage
        )
        
        viewModel.onEvent(CalculatorAction.ClearError)
        Assert.assertNull(viewModel.uiState.errorMessage)
    }

    private fun mockConvertToCurrency(amount: String) {
        coEvery { convertToCurrencyUseCase.convertToCurrency(any(), any(), amount) } returns
                Resource.Success(data = Currency(amount.toDouble()))
    }
}
