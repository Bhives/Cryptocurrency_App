package com.vironit.garbuzov_cryptocurrency.api

import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency

data class CryptoCurrenciesResults (
    val results: List<CryptoCurrency>
)