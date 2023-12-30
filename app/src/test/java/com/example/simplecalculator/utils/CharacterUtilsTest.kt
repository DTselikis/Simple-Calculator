package com.example.simplecalculator.utils

import org.junit.Assert
import org.junit.Test

class CharacterUtilsTest {

    @Test
    fun `is operation symbol`() {
        Assert.assertTrue('+'.isOperationSymbol())
        Assert.assertTrue('-'.isOperationSymbol())
        Assert.assertTrue('X'.isOperationSymbol())
        Assert.assertTrue('/'.isOperationSymbol())
    }

    @Test
    fun `is not operation symbol`() {
        Assert.assertFalse('!'.isOperationSymbol())
        Assert.assertFalse('_'.isOperationSymbol())
        Assert.assertFalse('x'.isOperationSymbol())
        Assert.assertFalse('*'.isOperationSymbol())
        Assert.assertFalse('\\'.isOperationSymbol())
        Assert.assertFalse('%'.isOperationSymbol())
    }

    @Test
    fun `has precedence`() {
        Assert.assertTrue('X'.hasNotPrecedence('X'))
        Assert.assertTrue('X'.hasNotPrecedence('/'))
        Assert.assertTrue('/'.hasNotPrecedence('X'))
        Assert.assertTrue('/'.hasNotPrecedence('/'))
        Assert.assertTrue('+'.hasNotPrecedence('X'))
        Assert.assertTrue('+'.hasNotPrecedence('/'))
        Assert.assertTrue('-'.hasNotPrecedence('/'))
        Assert.assertTrue('-'.hasNotPrecedence('/'))
    }

    @Test
    fun `has not precedence`() {
        Assert.assertFalse('X'.hasNotPrecedence('+'))
        Assert.assertFalse('X'.hasNotPrecedence('-'))
        Assert.assertFalse('/'.hasNotPrecedence('+'))
        Assert.assertFalse('/'.hasNotPrecedence('-'))
    }
}
