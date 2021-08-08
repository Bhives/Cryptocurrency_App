package com.vironit.garbuzov_cryptocurrency.viewmodels.notifications

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vironit.garbuzov_cryptocurrency.api.services.NotificationService
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import com.vironit.garbuzov_cryptocurrency.data.entities.ConvertedCryptoCurrency
import com.vironit.garbuzov_cryptocurrency.utils.NotificationServiceRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.NotSerializableException
import javax.inject.Inject

const val CHANNEL_ID: String = "CHANNEL_ID"
const val NOTIFICATION_ID = 101

@HiltViewModel
class AddNotificationViewModel @Inject constructor(
    var cryptoCurrencyRepository: CryptoCurrencyRepository, application: Application
) : AndroidViewModel(application) {

    val notificationService = NotificationService(cryptoCurrencyRepository)::class.java

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

    fun createNotification(
        context: Context,
        notificationName: String,
        requiredPercent: Double,
        currencySymbol: String,
        setVibration: Boolean,
        stonksFlag: Int
    ) {
        try {
            val serviceIntent = Intent(context, notificationService)
            serviceIntent.putExtra("notificationName", notificationName)
            serviceIntent.putExtra("requiredPercent", requiredPercent)
            serviceIntent.putExtra("currencySymbol", currencySymbol)
            serviceIntent.putExtra("setVibration", setVibration)
            serviceIntent.putExtra("stonksFlag", stonksFlag)
            context.startService(serviceIntent)
        } catch (iOException: IOException) {
            iOException.printStackTrace()
        } catch (notSerializableException: NotSerializableException) {
            notSerializableException.printStackTrace()
        }
    }
}