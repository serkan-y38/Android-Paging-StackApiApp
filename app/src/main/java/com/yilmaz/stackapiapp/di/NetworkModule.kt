package com.yilmaz.stackapiapp.di

import com.yilmaz.stackapiapp.data.remote.api_service.StackApiService
import com.yilmaz.stackapiapp.data.remote.repository.QuestionsRepositoryImpl
import com.yilmaz.stackapiapp.domain.repository.QuestionsRepository
import com.yilmaz.stackapiapp.utils.Constants.STACK_EXCHANGE_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(STACK_EXCHANGE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideStackApiService(
        retrofit: Retrofit
    ): StackApiService = retrofit.create(StackApiService::class.java)

    @Provides
    @Singleton
    fun provideQuestionsRepository(
        stackApiService: StackApiService
    ): QuestionsRepository = QuestionsRepositoryImpl(stackApiService)

}