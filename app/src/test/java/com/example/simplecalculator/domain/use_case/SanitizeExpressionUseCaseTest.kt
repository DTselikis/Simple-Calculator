package com.example.simplecalculator.domain.use_case

import junit.framework.TestCase.assertEquals
import org.junit.Test

class SanitizeExpressionUseCaseTest {

    private val sanitizeExpressionUseCase = SanitizeExpressionUseCase()

    @Test
    fun `sanitizing expression starting with zero replaces with digit`() {
        val result = sanitizeExpressionUseCase.sanitize("0", '4')
        assertEquals("4", result)
    }

    @Test
    fun `sanitizing expression starting with zero appends decimal point`() {
        val result = sanitizeExpressionUseCase.sanitize("0", '.')
        assertEquals("0.", result)
    }

    @Test
    fun `sanitizing expression starting with zero appends operation symbol`() {
        val result = sanitizeExpressionUseCase.sanitize("0", '+')
        assertEquals("0+", result)
    }

    @Test
    fun `sanitizing expression starting with zero ignores zero`() {
        val result = sanitizeExpressionUseCase.sanitize("0", '0')
        assertEquals("0", result)
    }

    @Test
    fun `sanitizing expression ending with digit appends digit`() {
        val result = sanitizeExpressionUseCase.sanitize("123", '4')
        assertEquals("1234", result)
    }

    @Test
    fun `sanitizing expression with decimal point ignores decimal point`() {
        val result = sanitizeExpressionUseCase.sanitize("12.34", '.')
        assertEquals("12.34", result)
    }

    @Test
    fun `sanitizing expression with operation symbol appends operation symbol`() {
        val result = sanitizeExpressionUseCase.sanitize("12", '+')
        assertEquals("12+", result)
    }

    @Test
    fun `sanitizing expression ending with operation symbol ignores operation symbol`() {
        val result = sanitizeExpressionUseCase.sanitize("12+", '*')
        assertEquals("12+", result)
    }

    @Test
    fun `sanitizing expression with decimal point and ending with operation symbol ignores decimal point`() {
        val result = sanitizeExpressionUseCase.sanitize("12.34+", '.')
        assertEquals("12.34+", result)
    }

    @Test
    fun `sanitizing expression with decimal point and ending with operation symbol ignores operation symbol`() {
        val result = sanitizeExpressionUseCase.sanitize("12.34+", '*')
        assertEquals("12.34+", result)
    }
}
