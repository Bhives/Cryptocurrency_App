package com.vironit.garbuzov_cryptocurrency.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency

@Dao
interface FavoriteCryptoCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToFavorites(cryptoCurrency: CryptoCurrency)

    @Update
    fun updateFavoriteCurrency(cryptoCurrency: CryptoCurrency)

    @Query("SELECT * FROM crypto_currency")
    fun getAllFavoriteCryptoCurrencies(): LiveData<List<CryptoCurrency>>

    @Query("SELECT * FROM crypto_currency WHERE symbol=:symbol")
    fun getFavoriteCryptoCurrency(symbol: String): LiveData<CryptoCurrency>

    @Delete
    fun removeFromFavorites(cryptoCurrency: CryptoCurrency)
}