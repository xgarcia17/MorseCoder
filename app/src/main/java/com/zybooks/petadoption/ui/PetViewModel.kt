package com.zybooks.petadoption.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.data.MorseCoderDataSource
import com.zybooks.petadoption.data.Pet

class MorseCoderViewModel : ViewModel() {
   val petList = MorseCoderDataSource().loadPets()
   var selectedPet by mutableStateOf(Pet())
}