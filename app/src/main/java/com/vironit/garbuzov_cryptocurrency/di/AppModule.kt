package com.vironit.garbuzov_cryptocurrency.di

import android.app.Application
import androidx.room.Room
import com.vironit.garbuzov_cryptocurrency.api.CryptoCurrencyApi
import com.vironit.garbuzov_cryptocurrency.data.database.FavoriteCryptoCurrenciesDatabase
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

    @Provides
    @Singleton
    fun provideDatabase(application: Application): FavoriteCryptoCurrenciesDatabase {
        return Room
            .databaseBuilder(application, FavoriteCryptoCurrenciesDatabase::class.java, "favorite_crypto_currencies.db")
            .build()
    }
}