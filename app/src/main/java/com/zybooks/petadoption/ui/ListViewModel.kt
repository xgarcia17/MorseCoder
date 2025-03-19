package com.zybooks.petadoption.ui

import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.MorseCoderDataSource

class ListViewModel : ViewModel() {
    val levelList = MorseCoderDataSource().loadLevels()
}