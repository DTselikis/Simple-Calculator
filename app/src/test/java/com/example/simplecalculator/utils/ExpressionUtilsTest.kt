package com.example.simplecalculator.utils

import org.junit.Assert
import org.junit.Test

class ExpressionUtilsTest {

    @Test
    fun `last number contains decimal point`() {
        Assert.assertTrue("2.0".lastNumberContainsDecimalPoint())
        Assert.assertTrue("2+5.1".lastNumberContainsDecimalPoint())
    }

    @Test
    fun `last number does not contains decimal point`() {
        Assert.assertFalse("2".lastNumberContainsDecimalPoint())
        Assert.assertFalse("2.1+3".lastNumberContainsDecimalPoint())
    }

    @Test
    fun `ends with operation symbol or decimal point`() {
        Assert.assertTrue("2+".endsWithOperationSymbolOrDecimalPoint())
        Assert.assertTrue("2.".endsWithOperationSymbolOrDecimalPoint())
        Assert.assertTrue("2.1+".endsWithOperationSymbolOrDecimalPoint())
        Assert.assertTrue("2+1.".endsWithOperationSymbolOrDecimalPoint())
    }

    @Test
    fun `not ends with operation symbol or decimal point`() {
        Assert.assertFalse("2".endsWithOperationSymbolOrDecimalPoint())
        Assert.assertFalse("2.3".endsWithOperationSymbolOrDecimalPoint())
        Assert.assertFalse("2.1+4".endsWithOperationSymbolOrDecimalPoint())
    }

    @Test
    fun `replace empty with zero`() {
        val tokens = listOf("1", "+", "2", "")
        Assert.assertEquals("1+20", tokens.replaceLastEmptyWithZero().joinToString(separator = ""))
    }

    @Test
    fun `not replace empty with zero`() {
        val tokens = listOf("1", "+", "2", "4")
        Assert.assertEquals("1+24", tokens.replaceLastEmptyWithZero().joinToString(separator = ""))
    }
}
