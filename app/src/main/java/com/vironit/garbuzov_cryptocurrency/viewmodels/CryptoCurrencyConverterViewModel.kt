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

    suspend fun convertCryptoCurrency(amount: Double, symbol: String): Double {
        return cryptoCurrencyRepository.getCurrentCryptoCurrency(amount, symbol).quote.getValue("USD").price
    }

    @SuppressLint("NewApi")
    fun getCurrentDate(): String {
        //return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        return ""
    }
}