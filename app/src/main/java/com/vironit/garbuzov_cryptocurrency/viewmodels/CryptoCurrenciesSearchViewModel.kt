package com.vironit.garbuzov_cryptocurrency.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoCurrenciesSearchViewModel @Inject constructor(cryptoCurrencyRepository: CryptoCurrencyRepository) :
    ViewModel() {

    val getCryptoCurrencies =
        cryptoCurrencyRepository.getCryptoCurrenciesResults().cachedIn(viewModelScope)
}