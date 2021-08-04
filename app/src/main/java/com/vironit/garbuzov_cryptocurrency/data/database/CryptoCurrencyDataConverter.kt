package com.vironit.garbuzov_cryptocurrency.data.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vironit.garbuzov_cryptocurrency.data.entities.CryptoCurrency
import java.lang.reflect.Type
import java.util.stream.Collectors

class CryptoCurrencyDataConverter {

    @RequiresApi(Build.VERSION_CODES.N)
    @TypeConverter
    fun fromTags(tags: List<String>): String? {
        return tags.joinToString(",")
    }

    @TypeConverter
    fun toTags(tags: String): List<String> {
        return listOf(tags.split(",").toString())
    }

    @TypeConverter
    fun fromPlatform(platform: CryptoCurrency.Platform): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<CryptoCurrency.Platform>() {}.type
        return gson.toJson(platform, type)
    }

    @TypeConverter
    fun toPlatform(platform: String?): CryptoCurrency.Platform? {
        if (platform == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<CryptoCurrency.Platform>() {}.type
        return gson.fromJson<CryptoCurrency.Platform>(platform, type)
    }

    @TypeConverter
    fun toMap(map: String): Map<String, CryptoCurrency.Quote> {
        return Gson().fromJson(
            map,
            object : TypeToken<Map<String, CryptoCurrency.Quote>>() {}.type
        )
    }

    @TypeConverter
    fun fromMap(map: Map<String, CryptoCurrency.Quote>): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun fromQuote(quote: CryptoCurrency.Quote): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<CryptoCurrency.Quote>() {}.type
        return gson.toJson(quote, type)
    }

    @TypeConverter
    fun toQuote(quote: String?): CryptoCurrency.Quote? {
        if (quote == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<CryptoCurrency.Quote>() {}.type
        return gson.fromJson<CryptoCurrency.Quote>(quote, type)
    }
}