package com.gotov.getmeapp.app.preference

import android.content.Context

class AppPreferences(context: Context) {
    companion object {
        private const val PERF_VAL = "app"
        const val Cookies = "cookies"
        const val Skills = "skills"
    }

    private val prefs = context.getSharedPreferences(PERF_VAL, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun putHashSet(key: String, value: HashSet<String>?) {
        prefs.edit().putStringSet(key, value).apply()
    }

    fun getHashSet(key: String): HashSet<String>? {
        prefs.getStringSet(key, hashSetOf())?.let {
            return HashSet(it)
        }
        return null
    }

    fun getInt(key: String, default: Int): Int {
        return prefs.getInt(key, default)
    }

    fun getString(key: String): String {
        return getNullableString(key) ?: ""
    }

    fun getNullableString(key: String): String? {
        return prefs.getString(key, null)
    }
}
