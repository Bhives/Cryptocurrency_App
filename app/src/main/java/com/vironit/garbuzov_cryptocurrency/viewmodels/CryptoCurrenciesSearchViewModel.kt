package com.vironit.garbuzov_cryptocurrency.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoCurrenciesSearchViewModel @Inject constructor(var cryptoCurrencyRepository: CryptoCurrencyRepository) :
    ViewModel() {

    fun getCryptoCurrencies(): LiveData<PagingData<CryptoCurrency>> {
        return cryptoCurrencyRepository.getCryptoCurrenciesResults().cachedIn(viewModelScope)
    }
}