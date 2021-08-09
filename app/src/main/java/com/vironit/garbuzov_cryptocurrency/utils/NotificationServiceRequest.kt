package com.vironit.garbuzov_cryptocurrency.utils

import com.vironit.garbuzov_cryptocurrency.api.CryptoCurrencyApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationServiceRequest {

    suspend fun serverRequest(
        cryptoCurrencySymbol: String,
        currencySymbol: String
    ): Double {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CryptoCurrencyApi.BASE_URL)
            .build()
            .create(CryptoCurrencyApi::class.java)
        val retrofitData = retrofitBuilder.getCurrentCryptoCurrency(1.0, cryptoCurrencySymbol, currencySymbol)
        return retrofitData.data.quote[currencySymbol]?.price!!
    }
}