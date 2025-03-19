package com.zybooks.petadoption

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.petadoption.ui.MorseCoderApp
import com.zybooks.petadoption.ui.OrientationViewModel
import com.zybooks.petadoption.ui.theme.MorseCoderTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         val orientationViewModel: OrientationViewModel = viewModel()

         val configuration = LocalConfiguration.current
         val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

         // Update ViewModel based on orientation change
         orientationViewModel.updateOrientation(isLandscape)
         MorseCoderTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               MorseCoderApp(orientationViewModel)
            }
         }
      }
   }
}
