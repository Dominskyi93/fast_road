package com.chicken.fast.road.util

import android.content.Context

const val DESTINATION = "destination"
const val DIFFICULT = "difficult"
const val SCORE = "score"
const val MUSIC = "music"
const val SOUND = "sound"
const val STRING = "string"

class SharPreference(private val context: Context) {
    fun getSkin(): Int {
        val prefs = context.getSharedPreferences(DESTINATION, Context.MODE_PRIVATE)
        return prefs.getInt(DESTINATION, 0)
    }

    fun saveSkin(skin: Int) {
        val editor = context.getSharedPreferences(DESTINATION, Context.MODE_PRIVATE).edit()
        editor.putInt(DESTINATION, skin)
        editor.apply()
    }

    fun getScore(): Int {
        val prefs = context.getSharedPreferences(SCORE, Context.MODE_PRIVATE)
        return prefs.getInt(SCORE, 0)
    }

    fun saveScore(skin: Int) {
        val editor = context.getSharedPreferences(SCORE, Context.MODE_PRIVATE).edit()
        editor.putInt(SCORE, skin)
        editor.apply()
    }

    fun getMusicEnabled(): Boolean {
        val prefs = context.getSharedPreferences(MUSIC, Context.MODE_PRIVATE)
        return prefs.getBoolean(MUSIC, true)
    }

    fun saveMusicEnabled(enabled: Boolean) {
        val editor = context.getSharedPreferences(MUSIC, Context.MODE_PRIVATE).edit()
        editor.putBoolean(MUSIC, enabled)
        editor.apply()
    }

    fun getSoundEnabled(): Boolean {
        val prefs = context.getSharedPreferences(SOUND, Context.MODE_PRIVATE)
        return prefs.getBoolean(SOUND, true)
    }

    fun saveSoundEnabled(enabled: Boolean) {
        val editor = context.getSharedPreferences(SOUND, Context.MODE_PRIVATE).edit()
        editor.putBoolean(SOUND, enabled)
        editor.apply()
    }

    fun getString(): String {
        val prefs = context.getSharedPreferences(STRING, Context.MODE_PRIVATE)
        return prefs.getString(STRING, "") ?: ""
    }

    fun save(string: String) {
        val editor = context.getSharedPreferences(STRING, Context.MODE_PRIVATE).edit()
        editor.putString(STRING, string)
        editor.apply()
    }

    fun getDifficult(): Boolean {
        val prefs = context.getSharedPreferences(DIFFICULT, Context.MODE_PRIVATE)
        return prefs.getBoolean(DIFFICULT, false)
    }

    fun saveDifficult(enabled: Boolean) {
        val editor = context.getSharedPreferences(DIFFICULT, Context.MODE_PRIVATE).edit()
        editor.putBoolean(DIFFICULT, enabled)
        editor.apply()
    }
}