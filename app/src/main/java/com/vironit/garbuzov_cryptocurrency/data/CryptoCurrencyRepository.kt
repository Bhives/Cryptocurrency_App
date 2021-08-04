package com.vironit.garbuzov_cryptocurrency.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vironit.garbuzov_cryptocurrency.api.CryptoCurrencyApi
import com.vironit.garbuzov_cryptocurrency.data.database.FavoriteCryptoCurrenciesDatabase
import com.vironit.garbuzov_cryptocurrency.data.entities.ConvertedCryptoCurrency
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoCurrencyRepository @Inject constructor(
    private val cryptoCurrencyApi: CryptoCurrencyApi,
    private val favoriteCryptoCurrenciesDatabase: FavoriteCryptoCurrenciesDatabase
) {

    fun getCryptoCurrenciesResults() = Pager(
        config = PagingConfig(
            pageSize = 1,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CryptoCurrenciesPagingSource(cryptoCurrencyApi) }
    ).liveData

    suspend fun getCurrentCryptoCurrency(amount: Double, symbol: String): ConvertedCryptoCurrency {
        return cryptoCurrencyApi.getCurrentCryptoCurrency(amount, symbol).data
    }

    fun insertToFavorites(cryptoCurrency: CryptoCurrency) =
        favoriteCryptoCurrenciesDatabase.favoriteCryptoCurrencyDao()
            .insertToFavorites(cryptoCurrency)

    fun updateFavoriteCurrency(cryptoCurrency: CryptoCurrency) =
        favoriteCryptoCurrenciesDatabase.favoriteCryptoCurrencyDao()
            .updateFavoriteCurrency(cryptoCurrency)

    fun getAllFavoriteCryptoCurrencies() =
        favoriteCryptoCurrenciesDatabase.favoriteCryptoCurrencyDao()
            .getAllFavoriteCryptoCurrencies()

    fun getFavoriteCryptoCurrency(symbol: String) =
        favoriteCryptoCurrenciesDatabase.favoriteCryptoCurrencyDao()
            .getFavoriteCryptoCurrency(symbol)

    fun removeFromFavorites(cryptoCurrency: CryptoCurrency) =
        favoriteCryptoCurrenciesDatabase.favoriteCryptoCurrencyDao()
            .removeFromFavorites(cryptoCurrency)
}