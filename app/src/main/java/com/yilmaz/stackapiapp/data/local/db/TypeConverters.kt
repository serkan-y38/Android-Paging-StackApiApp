package com.yilmaz.stackapiapp.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {

    @TypeConverter
    fun fromStringList(value: MutableList<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    /*
    @TypeConverter
    fun ownerToString(owner: Owner): String {
        return Gson().toJson(owner)
    }

    @TypeConverter
    fun stringToOwner(str: String): Owner {
        return Gson().fromJson(str, Owner::class.java)
    }
     */

}