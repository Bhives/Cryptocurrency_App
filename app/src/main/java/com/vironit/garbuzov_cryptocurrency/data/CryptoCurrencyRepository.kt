package com.vironit.garbuzov_cryptocurrency.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vironit.garbuzov_cryptocurrency.api.CryptoCurrencyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoCurrencyRepository @Inject constructor(
    private val cryptoCurrencyApi: CryptoCurrencyApi
) {

    fun getCryptoCurrenciesResults() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CryptoCurrenciesPagingSource(cryptoCurrencyApi) }
    ).liveData
}