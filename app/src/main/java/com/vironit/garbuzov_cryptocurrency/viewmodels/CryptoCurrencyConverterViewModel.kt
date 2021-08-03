package com.vironit.garbuzov_cryptocurrency.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoCurrencyConverterViewModel @Inject constructor(var cryptoCurrencyRepository: CryptoCurrencyRepository) :
    ViewModel() {

    suspend fun convertCryptoCurrency(amount: Double, cryptoCurrencySymbol: String, currencySymbol: String): Double {
        return cryptoCurrencyRepository.getCurrentCryptoCurrency(amount, cryptoCurrencySymbol).quote.getValue(currencySymbol).price
    }

    @SuppressLint("NewApi")
    fun getCurrentDate(): String {
        //return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        return ""
    }
}