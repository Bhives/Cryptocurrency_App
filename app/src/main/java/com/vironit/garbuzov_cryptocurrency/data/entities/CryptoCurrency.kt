package com.vironit.garbuzov_cryptocurrency.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "crypto_currency")
@Parcelize
data class CryptoCurrency(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "symbol") val symbol: String?,
    @ColumnInfo(name = "slug") val slug: String?,
    @ColumnInfo(name = "cmc_rank") val cmcRank: String?,
    @ColumnInfo(name = "num_market_pairs") val numMarketPairs: String?,
    @ColumnInfo(name = "circulating_supply") val circulatingSupply: String?,
    @ColumnInfo(name = "total_supply") val totalSupply: String?,
    @ColumnInfo(name = "max_supply") val maxSupply: String?,
    @ColumnInfo(name = "last_updated") val lastUpdated: String?,
    @ColumnInfo(name = "date_added") val dateAdded: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "volume_24h") val volume24H: String?,
    @ColumnInfo(name = "volume_7d") val volume7D: String?,
    @ColumnInfo(name = "volume_30d") val volume30D: String?,
    @ColumnInfo(name = "percent_change_1h") val percentChange1H: String?,
    @ColumnInfo(name = "percent_change_24h") val percentChange24H: String?,
    @ColumnInfo(name = "percent_change_7d") val percentChange7D: String?,
    @ColumnInfo(name = "market_cap") val marketCap: String?
): Parcelable
