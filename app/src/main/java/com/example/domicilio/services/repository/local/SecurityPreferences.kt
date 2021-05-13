package com.example.domicilio.services.repository.local

import android.content.Context

class SecurityPreferences(context: Context) {

    private val mSharedPreferences = context.getSharedPreferences("MobileHealth", Context.MODE_PRIVATE)

    fun store(key: String, value: String){
        mSharedPreferences.edit().putString(key,value).apply()
    }

    fun remove(key: String){
        mSharedPreferences.edit().remove(key).apply()
    }

    fun get(key: String) : String {
        return mSharedPreferences.getString(key, "") ?: ""
    }

}