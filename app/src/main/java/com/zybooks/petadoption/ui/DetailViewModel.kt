package com.zybooks.petadoption.ui

import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.Pet
import com.zybooks.petadoption.data.MorseCoderDataSource

class DetailViewModel : ViewModel() {
    fun getPet(id: Int): Pet = MorseCoderDataSource().getPet(id) ?: Pet()
}