package com.vironit.garbuzov_cryptocurrency.api

import android.os.Parcelable
import com.vironit.garbuzov_cryptocurrency.data.entities.ConvertedCryptoCurrency
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConvertedCryptoCurrencyResult(
    val data: ConvertedCryptoCurrency,
    val status: Status
) : Parcelable {
    @Parcelize
    data class Status(
        val timestamp: String?,
        val errorCode: Int,
        val errorMessage: String?,
        val elapsed: Int,
        val creditCount: Int
    ) : Parcelable
}