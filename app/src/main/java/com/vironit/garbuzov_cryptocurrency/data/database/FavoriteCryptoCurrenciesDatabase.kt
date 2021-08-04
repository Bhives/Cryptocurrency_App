package com.vironit.garbuzov_cryptocurrency.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import com.vironit.garbuzov_cryptocurrency.di.AppModule

@Database(entities = [CryptoCurrency::class], version = 1)
@TypeConverters(CryptoCurrencyDataConverter::class)
abstract class FavoriteCryptoCurrenciesDatabase : RoomDatabase() {
    abstract fun favoriteCryptoCurrencyDao(): FavoriteCryptoCurrencyDao

    companion object {
        @Volatile
        private var instance: FavoriteCryptoCurrenciesDatabase? = null
        private val LOCK = Any()

        operator fun invoke() = instance ?: synchronized(LOCK) {
            instance ?: AppModule.provideDatabase(Application()).also {
                instance = it
                it.close()
            }
        }
    }
}