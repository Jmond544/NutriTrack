package com.example.nutritrack.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("NutriTrackPrefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("USER_TOKEN", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("USER_TOKEN", null)
    }

    fun clearToken() {
        prefs.edit().remove("USER_TOKEN").apply()
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}