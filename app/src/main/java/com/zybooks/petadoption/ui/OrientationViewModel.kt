package com.zybooks.petadoption.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OrientationViewModel : ViewModel() {
    var orientation by mutableStateOf("portrait")

    fun updateOrientation(isLandscape: Boolean) {
        orientation = if (isLandscape) "landscape" else "portrait"
    }
}