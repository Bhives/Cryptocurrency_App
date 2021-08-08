package com.vironit.garbuzov_cryptocurrency.utils

import androidx.lifecycle.MutableLiveData
import com.vironit.garbuzov_cryptocurrency.api.CryptoCurrencyApi
import com.vironit.garbuzov_cryptocurrency.data.entities.ConvertedCryptoCurrency
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationServiceRequest {

    suspend fun serverRequest(
        currencySymbol: String
    ): Double {
        val resultLiveData = MutableLiveData<ConvertedCryptoCurrency>()
        var resultValue = 0.0
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(CryptoCurrencyApi.BASE_URL)
            .build()
            .create(CryptoCurrencyApi::class.java)
        val retrofitData = retrofitBuilder.getCurrentCryptoCurrency(1.0, currencySymbol)
        resultLiveData.postValue(retrofitData.data)
        resultValue = resultLiveData.value?.quote?.get("USD")?.price!!
        return resultValue
    }
}