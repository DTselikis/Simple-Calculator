package com.example.simplecalculator.di

import com.example.simplecalculator.data.remote.api.BeaconApi
import com.example.simplecalculator.data.remote.repository.BeaconRepository
import com.example.simplecalculator.domain.repository.CurrenciesRepository
import com.example.simplecalculator.domain.use_case.CalculateExpressionUseCase
import com.example.simplecalculator.domain.use_case.ConvertToCurrencyUseCase
import com.example.simplecalculator.domain.use_case.GetAvailableCurrenciesUseCase
import com.example.simplecalculator.domain.use_case.SanitizeExpressionUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val MEDIA_TYPE = "application/json"

    @Provides
    @Singleton
    fun provideKotlinSerialization(): Converter.Factory {
        val contentType = MediaType.parse(MEDIA_TYPE)!!
        val json = Json {
            ignoreUnknownKeys = true
        }
        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun provideBeaconApi(converterFactory: Converter.Factory): BeaconApi =
        Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(BeaconApi.BASE_URL)
            .build()
            .create(BeaconApi::class.java)

    @Provides
    @Singleton
    fun provideBeaconRepository(api: BeaconApi, @IoDispatcher ioDispatcher: CoroutineContext): BeaconRepository =
        BeaconRepository(api, ioDispatcher)

    @Provides
    @Singleton
    fun provideGetAvailableCurrenciesUseCase(currenciesRepository: CurrenciesRepository): GetAvailableCurrenciesUseCase =
        GetAvailableCurrenciesUseCase(currenciesRepository)

    @Provides
    @Singleton
    fun provideConvertToCurrencyUseCase(currenciesRepository: CurrenciesRepository): ConvertToCurrencyUseCase =
        ConvertToCurrencyUseCase(currenciesRepository)

    @Provides
    @Singleton
    fun provideSanitizeExpressionUseCase(): SanitizeExpressionUseCase =
        SanitizeExpressionUseCase()

    @Provides
    @Singleton
    fun provideCalculateExpressionUseCase(): CalculateExpressionUseCase =
        CalculateExpressionUseCase()
}
