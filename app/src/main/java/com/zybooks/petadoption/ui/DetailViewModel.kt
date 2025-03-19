package com.zybooks.petadoption.ui

import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.Level
import com.zybooks.petadoption.data.MorseCoderDataSource

class DetailViewModel : ViewModel() {
    fun getLevel(id: Int): Level = MorseCoderDataSource().getLevel(id) ?: Level()
}