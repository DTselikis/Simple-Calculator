package com.example.simplecalculator.data.remote.repository

import com.example.simplecalculator.consts.EURO_SHORT_CODE
import com.example.simplecalculator.data.local.LocalCurrenciesDataSource
import com.example.simplecalculator.data.remote.repository.api.FakeApi
import com.example.simplecalculator.helper.ErrorResolver
import com.example.simplecalculator.helper.Resource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BeaconRepositoryTest {

    @Test
    fun `getAvailableCurrencies should return Success when API call is successful`() = runTest {
        val beaconRepository = BeaconRepository(
            FakeApi(),
            StandardTestDispatcher(testScheduler)
        )
        val response = beaconRepository.getAvailableCurrencies()
        advanceUntilIdle()

        Assert.assertTrue(response is Resource.Success)
        Assert.assertNotNull(response.data)
        response.data!!.forEach { currencyInfo ->
            LocalCurrenciesDataSource.availableCurrencies.any { it.id == currencyInfo.id }
        }
    }

    @Test
    fun `getAvailableCurrencies should return Error when API call is successful`() = runTest {
        val beaconRepository = BeaconRepository(
            FakeApi(shouldReturnError = true),
            StandardTestDispatcher(testScheduler)
        )
        val response = beaconRepository.getAvailableCurrencies()
        advanceUntilIdle()

        Assert.assertTrue(response is Resource.Error)
        Assert.assertNull(response.data)
        Assert.assertEquals(ErrorResolver.CONNECTIVITY_ERROR, response.message)
    }

    @Test
    fun `convertToCurrency should return Success when API call is successful`() = runTest {
        val beaconRepository = BeaconRepository(
            FakeApi(),
            StandardTestDispatcher(testScheduler)
        )
        val response = beaconRepository.convertToCurrency(
            from = EURO_SHORT_CODE,
            to = "AUD",
            amount = 5.0
        )
        advanceUntilIdle()

        Assert.assertTrue(response is Resource.Success)
        Assert.assertNotNull(response.data)
        assertEquals(LocalCurrenciesDataSource.convertedCurrency.value, response.data!!.value)
    }

    @Test
    fun `convertToCurrency should return Error when API call is successful`() = runTest {
        val beaconRepository = BeaconRepository(
            FakeApi(shouldReturnError = true),
            StandardTestDispatcher(testScheduler)
        )
        val response = beaconRepository.convertToCurrency(
            from = EURO_SHORT_CODE,
            to = "AUD",
            amount = 5.0
        )
        advanceUntilIdle()

        Assert.assertTrue(response is Resource.Error)
        Assert.assertNull(response.data)
        Assert.assertEquals(ErrorResolver.CONNECTIVITY_ERROR, response.message)
    }
}