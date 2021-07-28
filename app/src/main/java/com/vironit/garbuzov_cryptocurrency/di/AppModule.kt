package com.vironit.garbuzov_cryptocurrency.di

import com.vironit.garbuzov_cryptocurrency.api.CryptoCurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(CryptoCurrencyApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideCryptoCurrencyApi(retrofit: Retrofit): CryptoCurrencyApi =
        retrofit.create(CryptoCurrencyApi::class.java)
}