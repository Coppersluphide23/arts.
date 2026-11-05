package com.example.arts.utils

import android.content.Context

object FavoritesManager {
    private const val PREF_NAME = "arts_favorites_prefs"

    fun isFavorite(context: Context, artId: String): Boolean {
        if (artId.isBlank()) return false
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(artId, false)
    }

    fun toggleFavorite(context: Context, artId: String): Boolean {
        if (artId.isBlank()) return false
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val currentState = prefs.getBoolean(artId, false)
        val newState = !currentState
        prefs.edit().putBoolean(artId, newState).apply()
        return newState
    }

    fun getFavoriteIds(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.all.filterValues { it == true }.keys
    }
}
