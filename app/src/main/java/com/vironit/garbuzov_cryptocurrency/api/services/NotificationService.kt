package com.vironit.garbuzov_cryptocurrency.api.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.provider.Settings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.data.CryptoCurrencyRepository
import com.vironit.garbuzov_cryptocurrency.utils.NotificationTemplate
import com.vironit.garbuzov_cryptocurrency.viewmodels.notifications.CHANNEL_ID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationService(var cryptoCurrencyRepository: CryptoCurrencyRepository) :
    Service() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    var currentValue = 0.0

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mHandler = Handler()
        mRunnable = Runnable {
            when {
                intent.getIntExtra("stonksFlag", 0) == 1 -> {
                    processPriceRising(
                        intent.getStringExtra("notificationName")!!,
                        intent.getDoubleExtra("requiredPercent", 0.0),
                        intent.getStringExtra("currencySymbol")!!,
                        intent.getBooleanExtra("setVibration", false)
                    )
                }
                intent.getIntExtra("stonksFlag", 0) == 0 -> {
                    processPriceLowering(
                        intent.getStringExtra("notificationName")!!,
                        intent.getDoubleExtra("requiredPercent", 0.0),
                        intent.getStringExtra("currencySymbol")!!,
                        intent.getBooleanExtra("setVibration", false)
                    )
                }
            }
        }
        mHandler.postDelayed(mRunnable, 1000)
        return START_STICKY
    }

    private fun processPriceRising(
        notificationName: String,
        requiredPercent: Double,
        currencySymbol: String,
        setVibration: Boolean
    ) {
        val newValue = serverRequest(currencySymbol)
        if (currentValue == 0.0) {
            currentValue = newValue
        }
        val stonksPercent = (newValue - currentValue) / 100
        if (stonksPercent >= requiredPercent) {
            createNotification(
                notificationName,
                requiredPercent,
                currencySymbol,
                setVibration,
                true
            )
        }
        currentValue = newValue
    }

    private fun processPriceLowering(
        notificationName: String,
        requiredPercent: Double,
        currencySymbol: String,
        setVibration: Boolean
    ) {
        val newValue = serverRequest(currencySymbol)
        if (currentValue == 0.0) {
            currentValue = newValue
        }
        val destonksPercent = (currentValue - newValue) / 100
        if (destonksPercent >= requiredPercent) {
            createNotification(
                notificationName,
                destonksPercent,
                currencySymbol,
                setVibration,
                false
            )
        }
        currentValue = newValue
    }

    private fun serverRequest(currencySymbol: String): Double {
        var result = 0.0
        GlobalScope.launch {
            result =
                cryptoCurrencyRepository.getCurrentCryptoCurrency(
                    1.0,
                    currencySymbol
                ).amount
        }
        return result
    }

    private fun createNotification(
        notificationName: String,
        receivedPercent: Double,
        currencySymbol: String,
        setVibration: Boolean,
        stonks: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = Firebase.auth.currentUser?.displayName
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, notificationName, importance).apply {
                description = descriptionText
            }
            with(channel) {
                enableLights(true)
                lightColor = R.color.red_app_color
                enableVibration(setVibration)
                vibrationPattern = longArrayOf(200, 200, 200, 200)
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI, null)
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        var notificationTitle = ""
        notificationTitle = if (stonks) {
            "$currencySymbol ${this.resources.getString(R.string.message_stonks)}"
        } else {
            "$currencySymbol ${this.resources.getString(R.string.message_destonks)}"
        }
        NotificationTemplate().initializeBuilder(
            this,
            notificationTitle,
            "$receivedPercent"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }
}