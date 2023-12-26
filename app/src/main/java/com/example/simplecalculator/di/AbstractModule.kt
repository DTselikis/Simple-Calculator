package com.example.simplecalculator.di

import com.example.simplecalculator.data.remote.repository.BeaconRepository
import com.example.simplecalculator.domain.repository.CurrenciesRepository
import com.example.simplecalculator.domain.use_case.CalculateExpressionUseCase
import com.example.simplecalculator.domain.use_case.ExpressionCalculator
import com.example.simplecalculator.domain.use_case.ExpressionSanitization
import com.example.simplecalculator.domain.use_case.SanitizeExpressionUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {
    @Binds
    @Singleton
    abstract fun bindExpressionCalculator(
        calculateExpressionUseCase: CalculateExpressionUseCase
    ): ExpressionCalculator

    @Binds
    @Singleton
    abstract fun bindExpressionAppender(
        sanitizeExpressionUseCase: SanitizeExpressionUseCase
    ): ExpressionSanitization

    @Binds
    @Singleton
    abstract fun bindCurrenciesRepository(
        beaconRepository: BeaconRepository
    ): CurrenciesRepository
}
