package com.example.simplecalculator.di

import com.example.simplecalculator.domain.use_case.CalculateExpressionUseCase
import com.example.simplecalculator.domain.use_case.ExpressionCalculator
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
}
