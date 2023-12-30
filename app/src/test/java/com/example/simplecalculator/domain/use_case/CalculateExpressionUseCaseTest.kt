package com.example.simplecalculator.domain.use_case

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CalculateExpressionUseCaseTest {

    private val calculateExpressionUseCase = CalculateExpressionUseCase()

    @Test
    fun `simple addition`() {
        val result = calculateExpressionUseCase.calculate("2+3")
        assertEquals(5.0, result, 0.0001)
    }

    @Test
    fun `simple subtraction`() {
        val result = calculateExpressionUseCase.calculate("5-3")
        assertEquals(2.0, result, 0.0001)
    }

    @Test
    fun `simple multiplication`() {
        val result = calculateExpressionUseCase.calculate("2X3")
        assertEquals(6.0, result, 0.0001)
    }

    @Test
    fun `simple division`() {
        val result = calculateExpressionUseCase.calculate("6/3")
        assertEquals(2.0, result, 0.0001)
    }

    @Test
    fun `simple addition with decimal numbers`() {
        val result = calculateExpressionUseCase.calculate("1.5+2.5")
        assertEquals(4.0, result, 0.0001)
    }

    @Test
    fun `simple subtraction with decimal numbers`() {
        val result = calculateExpressionUseCase.calculate("8.1-2.5")
        assertEquals(5.6, result, 0.0001)
    }

    @Test
    fun `simple multiplication with decimal numbers`() {
        val result = calculateExpressionUseCase.calculate("18.3X12.3")
        assertEquals(225.09, result, 0.0001)
    }

    @Test
    fun `simple operation with integer and decimal number`() {
        val result = calculateExpressionUseCase.calculate("5-1.1")
        assertEquals(3.9, result, 0.0001)
    }

    @Test
    fun `simple operation with decimal and integer number`() {
        val result = calculateExpressionUseCase.calculate("29.1X2")
        assertEquals(58.2, result, 0.0001)
    }

    @Test
    fun `simple division with decimal numbers`() {
        val result = calculateExpressionUseCase.calculate("4.2/6.1")
        assertEquals(0.688524, result, 0.0001)
    }

    @Test
    fun `expression with multiple operations with same precedences`() {
        val result = calculateExpressionUseCase.calculate("2+3+4-5-2")
        assertEquals(2.0, result, 0.0001)
    }

    @Test
    fun `expression with multiple operations with different precedences`() {
        val result = calculateExpressionUseCase.calculate("2+3X4-5/2")
        assertEquals(11.5, result, 0.0001)
    }

    @Test
    fun `expression with multiple operations with different precedences with integes and decimal`() {
        val result = calculateExpressionUseCase.calculate("29.1-11+1.2X10-0.2/3+5")
        assertEquals(35.03333, result, 0.0001)
    }

    @Test
    fun `operation without second number`() {
        val result = calculateExpressionUseCase.calculate("29.1-5+")
        assertEquals(24.1, result, 0.0001)
    }

    @Test
    fun `division with zero`() {
        val exception = assertThrows(ArithmeticException::class.java) {
            calculateExpressionUseCase.calculate("5/0")
        }
        assertEquals("Cannot divide by zero", exception.message)
    }
}
