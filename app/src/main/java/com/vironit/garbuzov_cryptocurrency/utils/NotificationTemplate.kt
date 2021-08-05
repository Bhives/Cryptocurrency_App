package com.vironit.garbuzov_cryptocurrency.utils

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.viewmodels.notifications.CHANNEL_ID
import com.vironit.garbuzov_cryptocurrency.viewmodels.notifications.NOTIFICATION_ID

class NotificationTemplate {

    fun initializeBuilder(context: Context, notificationTitle: String, notificationText: String) {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }
}