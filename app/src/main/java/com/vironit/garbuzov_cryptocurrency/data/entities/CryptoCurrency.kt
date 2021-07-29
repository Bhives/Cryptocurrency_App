package com.vironit.garbuzov_cryptocurrency.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "crypto_currency")
@Parcelize
data class CryptoCurrency(
    @PrimaryKey @SerializedName("id") @Expose var id: Int,
    @SerializedName("name") @Expose val name: String?,
    @SerializedName("symbol") @Expose val symbol: String?,
    @SerializedName("slug") @Expose val slug: String?,
    @SerializedName("cmc_rank") @Expose val cmcRank: Int,
    @SerializedName("num_market_pairs") @Expose val numMarketPairs: Int,
    @SerializedName("circulating_supply") @Expose val circulatingSupply: Number,
    @SerializedName("total_supply") @Expose val totalSupply: Number,
    @SerializedName("market_cap_by_total_supply") @Expose val marketCapByTotalSupply: Number,
    @SerializedName("max_supply") @Expose val maxSupply: Number,
    @SerializedName("last_updated") @Expose val lastUpdated: String?,
    @SerializedName("date_added") @Expose val dateAdded: String?,
    @SerializedName("tags") @Expose val tags: List<String>,
    @SerializedName("platform") @Expose val platform: Platform,
    @SerializedName("quote") @Expose val quote: Map<String, Quote>,
) : Parcelable {
    @Parcelize
    data class Platform(
        @SerializedName ("id") @Expose val id: Int,
        @SerializedName ("name") @Expose val name: String?,
        @SerializedName ("symbol") @Expose val symbol: String?,
        @SerializedName ("slug") @Expose val slug: String?,
        @SerializedName ("token_address") @Expose val tokenAddress: String?,
    ) : Parcelable

    @Parcelize
    data class Quote(
        @SerializedName ("price") @Expose val price: Number,
        @SerializedName ("volume_24h") @Expose val volume24H: Number,
        @SerializedName ("volume_24h_reported") @Expose val volume24HReported: Number,
        @SerializedName ("volume_7d") @Expose val volume7D: Number,
        @SerializedName ("volume_7d_reported") @Expose val volume7DReported: Number,
        @SerializedName ("volume_30d") @Expose val volume30D: Number,
        @SerializedName ("volume_30d_reported") @Expose val volume30DReported: Number,
        @SerializedName ("market_cap") @Expose val marketCap: Number,
        @SerializedName ("percent_change_1h") @Expose val percentChange1H: Number,
        @SerializedName ("percent_change_24h") @Expose val percentChange24H: Number,
        @SerializedName ("percent_change_7d") @Expose val percentChange7D: Number,
        @SerializedName ("last_updated") @Expose val lastUpdated: String?
    ) : Parcelable
}
