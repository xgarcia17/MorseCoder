package com.zybooks.petadoption.ui

import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.Level
import com.zybooks.petadoption.data.MorseCoderDataSource

class LevelViewModel : ViewModel() {
    fun getLevel(id: Int): Level = MorseCoderDataSource().getLevel(id) ?: Level()

    fun getCharCode(id: Char): Any = MorseCoderDataSource().getCharCode(id) ?: String
}