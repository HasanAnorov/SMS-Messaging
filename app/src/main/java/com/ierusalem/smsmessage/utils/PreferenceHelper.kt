package com.ierusalem.smsmessage.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ierusalem.smsmessage.PhoneNumber

class PreferenceHelper(context: Context) {

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE)

    fun saveCategories(categories: Categories){
        with(sharedPref.edit()) {
            val userGson = Gson().toJson(categories)
            putString(Constants.CATEGORIES_KEY, userGson)
            apply()
        }
    }

    fun getCategories(): Categories{
        val defaultValue = Gson().toJson(Categories(listOf()))
        val categories = sharedPref.getString(Constants.CATEGORIES_KEY, defaultValue)
        return Gson().fromJson(categories, Categories::class.java)
    }

}

data class Categories(
    val list: List<Category>
)

data class Category(
    val name: String,
    val contacts: List<PhoneNumber>
)

