package co.com.corpacero.simulador.ui.components

import android.content.Context

object CalcStorage {
    private const val PREF = "calc_state"
    private const val MARKER_SUFFIX = "._saved"

    fun save(context: Context, calcKey: String, fields: Map<String, String>) {
        val edit = context.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit()
        fields.forEach { (k, v) -> edit.putString("$calcKey.$k", v) }
        edit.putBoolean("$calcKey$MARKER_SUFFIX", true)
        edit.apply()
    }

    fun load(context: Context, calcKey: String): Map<String, String>? {
        val prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        if (!prefs.getBoolean("$calcKey$MARKER_SUFFIX", false)) return null
        return prefs.all
            .filterKeys { it.startsWith("$calcKey.") && !it.endsWith(MARKER_SUFFIX) }
            .mapKeys { it.key.removePrefix("$calcKey.") }
            .mapValues { it.value as? String ?: "" }
    }

    fun clear(context: Context, calcKey: String) {
        val prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val toRemove = prefs.all.keys.filter {
            it.startsWith("$calcKey.") || it == "$calcKey$MARKER_SUFFIX"
        }
        prefs.edit().apply { toRemove.forEach { remove(it) } }.apply()
    }
}
