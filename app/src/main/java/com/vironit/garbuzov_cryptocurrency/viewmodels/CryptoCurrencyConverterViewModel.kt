package com.vironit.garbuzov_cryptocurrency.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoCurrencyConverterViewModel @Inject constructor(var cryptoCurrencyRepository: CryptoCurrencyRepository) :
    ViewModel() {

    private suspend fun getCurrentCryptoCurrency(cryptoCurrencyType: String): CryptoCurrency {
        return cryptoCurrencyRepository.getCurrentCryptoCurrency(cryptoCurrencyType)
    }

    suspend fun convertCryptoCurrency(amount: Double, cryptoCurrencyType: String, currencyType: String): Double {
        return amount * getCurrentCryptoCurrency(cryptoCurrencyType).quote.getValue(currencyType).price
    }

    @SuppressLint("NewApi")
    fun getCurrentDate(): String {
        //return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        return ""
    }
}