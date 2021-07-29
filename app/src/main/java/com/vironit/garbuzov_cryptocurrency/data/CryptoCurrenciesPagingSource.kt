package com.vironit.garbuzov_cryptocurrency.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vironit.garbuzov_cryptocurrency.api.CryptoCurrencyApi
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import retrofit2.HttpException
import java.io.IOException

private const val CRYPTOCURRENCY_STARTING_PAGE_INDEX = 1

class CryptoCurrenciesPagingSource(
    private val cryptoCurrencyApi: CryptoCurrencyApi
) :
    PagingSource<Int, CryptoCurrency>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoCurrency> {
        val currentPosition = params.key ?: CRYPTOCURRENCY_STARTING_PAGE_INDEX
        return try {
            val response =
                cryptoCurrencyApi.searchCryptoCurrencies(currentPosition, 100)
            val cryptoCurrencies = response.data
            LoadResult.Page(
                data = cryptoCurrencies,
                prevKey = if (currentPosition == CRYPTOCURRENCY_STARTING_PAGE_INDEX) null else currentPosition - 1,
                nextKey = if (cryptoCurrencies.isNullOrEmpty()) null else currentPosition + 1
            )
        } catch (iOException: IOException) {
            LoadResult.Error(iOException)
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CryptoCurrency>): Int? {
        TODO("Not yet implemented")
    }
}