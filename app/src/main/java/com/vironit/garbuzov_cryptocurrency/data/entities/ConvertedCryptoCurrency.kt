package com.vironit.garbuzov_cryptocurrency.data.entities

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConvertedCryptoCurrency(
    @PrimaryKey @SerializedName("id") @Expose var id: Int,
    @SerializedName("name") @Expose val name: String?,
    @SerializedName("symbol") @Expose val symbol: String?,
    @SerializedName("last_updated") @Expose val lastUpdated: String?,
    @SerializedName("amount") @Expose val amount: Double,
    @SerializedName("quote") @Expose val quote: Map<String, Quote>,
) : Parcelable {
    @Parcelize
    data class Quote(
        @SerializedName("price") @Expose val price: Double,
        @SerializedName("last_updated") @Expose val lastUpdated: String?
    ) : Parcelable
}
