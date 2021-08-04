package com.vironit.garbuzov_cryptocurrency.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency

@Dao
interface FavoriteCryptoCurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToFavorites(cryptoCurrency: CryptoCurrency)

    @Query("SELECT * FROM crypto_currency")
    fun getFavoriteCryptoCurrencies(): LiveData<List<CryptoCurrency>>

    @Delete
    fun removeFromFavorites(cryptoCurrency: CryptoCurrency)
}