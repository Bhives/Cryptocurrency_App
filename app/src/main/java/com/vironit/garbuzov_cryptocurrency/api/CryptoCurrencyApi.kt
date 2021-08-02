package com.vironit.garbuzov_cryptocurrency.api

import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CryptoCurrencyApi {

    @Headers("X-CMC_PRO_API_KEY: $COINMARKETCAP_KEY")
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getAllCryptoCurrencies(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): CryptoCurrenciesResult

    @Headers("X-CMC_PRO_API_KEY: $COINMARKETCAP_KEY")
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getCurrentCryptoCurrency(
        @Query("convert") symbol: String,
    ): CryptoCurrency

    companion object {
        const val BASE_URL = "https://pro-api.coinmarketcap.com"
        const val COINMARKETCAP_KEY = "c404869f-1fad-49bf-89e8-8674cedf351c"
    }
}