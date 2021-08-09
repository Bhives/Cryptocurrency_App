package com.vironit.garbuzov_cryptocurrency.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vironit.garbuzov_cryptocurrency.data.entities.CustomNotification

@Dao
interface CustomNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(customNotification: CustomNotification)

    @Query("SELECT * FROM custom_notification")
    fun getAllNotifications(): LiveData<List<CustomNotification>>

    @Delete
    fun deleteNotification(customNotification: CustomNotification)
}