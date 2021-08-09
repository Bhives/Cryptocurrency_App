package com.vironit.garbuzov_cryptocurrency.viewmodels.notifications

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vironit.garbuzov_cryptocurrency.api.services.NotificationService
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import com.vironit.garbuzov_cryptocurrency.data.entities.ConvertedCryptoCurrency
import com.vironit.garbuzov_cryptocurrency.data.entities.CustomNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.NotSerializableException
import javax.inject.Inject

const val CHANNEL_ID: String = "CHANNEL_ID"
const val NOTIFICATION_ID = 101

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    var cryptoCurrencyRepository: CryptoCurrencyRepository, application: Application
) : AndroidViewModel(application) {

    val notificationService = NotificationService::class.java

    fun getConvertedCryptoCurrency(
        amount: Double,
        cryptoCurrencySymbol: String,
        currencySymbol: String
    ): LiveData<ConvertedCryptoCurrency> {
        val result = MutableLiveData<ConvertedCryptoCurrency>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(
                cryptoCurrencyRepository.getCurrentCryptoCurrency(
                    amount,
                    cryptoCurrencySymbol,
                    currencySymbol
                )
            )
        }
        return result
    }

    fun insertNotification(customNotification: CustomNotification) {
        viewModelScope.launch(Dispatchers.IO) {
            cryptoCurrencyRepository.insertNotification(customNotification)
        }
    }

    fun getAllCustomNotifications(): LiveData<List<CustomNotification>> {
        var customNotificationsList =
            MutableLiveData(listOf<CustomNotification>()) as LiveData<List<CustomNotification>>
        viewModelScope.launch(Dispatchers.IO) {
            customNotificationsList = cryptoCurrencyRepository.getAllNotifications()
        }
        return customNotificationsList
    }

    fun createNotification(
        context: Context,
        notificationName: String,
        requiredPercent: Double,
        cryptoCurrencySymbol: String,
        currencySymbol: String,
        setVibration: Boolean,
        stonksFlag: Int
    ) {
        try {
            insertNotification(
                CustomNotification(
                    "$currencySymbol $requiredPercent%",
                    Firebase.auth.currentUser?.displayName.toString()
                )
            )
            val serviceIntent = Intent(context, notificationService)
            serviceIntent.putExtra("notificationName", notificationName)
            serviceIntent.putExtra("requiredPercent", requiredPercent)
            serviceIntent.putExtra("cryptoCurrencySymbol", cryptoCurrencySymbol)
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