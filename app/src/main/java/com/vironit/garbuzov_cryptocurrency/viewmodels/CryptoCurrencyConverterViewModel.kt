package com.vironit.garbuzov_cryptocurrency.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import com.vironit.garbuzov_cryptocurrency.data.entities.ConvertedCryptoCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoCurrencyConverterViewModel @Inject constructor(var cryptoCurrencyRepository: CryptoCurrencyRepository) :
    ViewModel() {

    fun getConvertedCryptoCurrency(
        amount: Double,
        cryptoCurrencySymbol: String
    ): LiveData<ConvertedCryptoCurrency> {
        val result = MutableLiveData<ConvertedCryptoCurrency>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(
                cryptoCurrencyRepository.getCurrentCryptoCurrency(
                    amount,
                    cryptoCurrencySymbol
                )
            )
        }
        return result
    }
}