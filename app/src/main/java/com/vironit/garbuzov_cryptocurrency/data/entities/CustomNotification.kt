package com.vironit.garbuzov_cryptocurrency.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "custom_notification")
@Parcelize
data class CustomNotification(
    @PrimaryKey @ColumnInfo(name = "notification_name") val notificationName: String,
    @ColumnInfo(name = "user_name") val userName: String?,
) : Parcelable
