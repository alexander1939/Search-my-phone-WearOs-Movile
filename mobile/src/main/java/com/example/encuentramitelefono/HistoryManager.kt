package com.example.encuentramitelefono

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HistoryManager {
    private const val PREFS_NAME = "search_history_prefs"
    private const val KEY_HISTORY = "search_history"

    fun addSearch(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val history = getHistory(context).toMutableList()
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        val entry = "Búsqueda a las $date"
        if (history.firstOrNull() == entry) return // No guardar duplicado inmediato
        history.add(0, entry)
        prefs.edit().putString(KEY_HISTORY, history.joinToString(";;;")).apply()
    }

    fun getHistory(context: Context): List<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_HISTORY, "") ?: ""
        return if (raw.isBlank()) emptyList() else raw.split(";;;")
    }

    fun removeSearch(context: Context, entry: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val history = getHistory(context).toMutableList()
        history.remove(entry)
        prefs.edit().putString(KEY_HISTORY, history.joinToString(";;;"))
            .apply()
    }
} 