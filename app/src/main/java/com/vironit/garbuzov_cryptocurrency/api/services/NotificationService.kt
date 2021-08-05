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
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.utils.NotificationTemplate
import com.vironit.garbuzov_cryptocurrency.viewmodels.notifications.AddNotificationViewModel
import com.vironit.garbuzov_cryptocurrency.viewmodels.notifications.CHANNEL_ID
import javax.inject.Inject

class NotificationService @Inject constructor(private val addNotificationViewModel: AddNotificationViewModel) :
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
            serverRequest(
                intent.getStringExtra("notificationName")!!,
                intent.getDoubleExtra("percent", 0.0),
                intent.getStringExtra("currencySymbol")!!,
                intent.getBooleanExtra("setVibration", false)
            )
        }
        mHandler.postDelayed(mRunnable, 1000)
        return START_STICKY
    }

    private fun serverRequest(
        notificationName: String,
        percent: Double,
        currencySymbol: String,
        setVibration: Boolean
    ) {
        val newValue = addNotificationViewModel.getConvertedCryptoCurrency(1.0, currencySymbol).value?.amount!!
        if ((currentValue/100){
            TODO("Continue from here!")
            }
        createNotification(notificationName, percent, currencySymbol, setVibration)
    }

    private fun createNotification(
        notificationName: String,
        percent: Double,
        currencySymbol: String,
        setVibration: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = R.string.channel_description.toString()
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, notificationName, importance).apply {
                description = descriptionText
            }
            with(channel) {
                enableLights(true)
                lightColor = R.color.red
                enableVibration(setVibration)
                vibrationPattern = longArrayOf(200, 200, 200, 200)
                setSound(Settings.System.DEFAULT_NOTIFICATION_URI, null)
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        NotificationTemplate().initializeBuilder(
            this,
            "$currencySymbol ${this.resources.getString(R.string.message_stonks)}",
            "$percent"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }
}