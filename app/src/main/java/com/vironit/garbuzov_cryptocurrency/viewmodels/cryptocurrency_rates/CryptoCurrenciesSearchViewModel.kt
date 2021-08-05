package com.vironit.garbuzov_cryptocurrency.viewmodels.cryptocurrency_rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoCurrenciesSearchViewModel @Inject constructor(var cryptoCurrencyRepository: CryptoCurrencyRepository) :
    ViewModel() {

    fun getCryptoCurrencies(): LiveData<PagingData<CryptoCurrency>> {
        return cryptoCurrencyRepository.getCryptoCurrenciesResults().cachedIn(viewModelScope)
    }

    fun insertToFavorites(cryptoCurrency: CryptoCurrency) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoCurrencyRepository.insertToFavorites(cryptoCurrency)
        }
    }

    fun updateFavoriteCurrency(cryptoCurrency: CryptoCurrency) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoCurrencyRepository.updateFavoriteCurrency(cryptoCurrency)
        }
    }

    fun getAllFavoriteCryptoCurrencies(): LiveData<List<CryptoCurrency>> {
        var favoriteCryptoCurrenciesList = MutableLiveData(listOf<CryptoCurrency>()) as LiveData<List<CryptoCurrency>>
        viewModelScope.launch(Dispatchers.IO)  {
            favoriteCryptoCurrenciesList = cryptoCurrencyRepository.getAllFavoriteCryptoCurrencies()
        }
        return favoriteCryptoCurrenciesList
    }

    fun removeFromFavorites(cryptoCurrency: CryptoCurrency) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoCurrencyRepository.removeFromFavorites(cryptoCurrency)
        }
    }
}