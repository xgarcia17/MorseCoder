package com.zybooks.petadoption.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MorseCoderApp(orientationViewModel: OrientationViewModel) {
   val navController = rememberNavController()

   NavHost(
      navController = navController,
      startDestination = "start_screen"
   ) {
      composable("start_screen") {
         Scaffold(
            topBar = {
               MorseCoderAppBar(
                  title = "Morse Coder",
                  canNavigateBack = false,
               )
            }
         ) {
            StartScreen(
               onNavigateToMessagePassing = { title ->
                  // Navigate to the MessengerScreen with the appropriate title
                  navController.navigate("messenger_screen/$title")
               },
               onNavigateToLearner = { title ->
                  // Navigate to the LearnerScreen with the appropriate title
                  navController.navigate("learner_screen/$title")
               }
            )
         }
      }

      composable("messenger_screen/{title}") { backstackEntry ->
         val title = backstackEntry.arguments?.getString("title") ?: "Default Title"

         MessengerScreen(
            title = title, // Pass the title to MessengerScreen
            onUpClick = {
               // Navigate back to the "start_screen"
               navController.navigateUp()  // This will navigate back to the start screen
            }
         )
      }

      composable("learner_screen/{title}") { backstackEntry ->
         val title = backstackEntry.arguments?.getString("title") ?: "Default Title"

         LearnerScreen(
            title = title, // Pass the title to LearnerScreen
            onButtonClick = { level ->
               navController.navigate("level_screen/${level.id}")
            },
            onUpClick = {
               // Navigate back to the "start_screen"
               navController.navigateUp()  // This will navigate back to the start screen
            }
         )
      }

      composable("level_screen/{levelId}") { backstackEntry ->
         val levelId = backstackEntry.arguments?.getString("levelId")?.toInt() ?: 0
         LevelScreen(
            levelId = levelId,
            onUpClick = {
               navController.navigateUp()
            }
         )
      }
   }
}

@Composable
fun StartScreen(
   onNavigateToMessagePassing: (String) -> Unit,
   onNavigateToLearner: (String) -> Unit
) {
   val config = LocalConfiguration.current
   val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE

   if (isLandscape) {
      Row(
         modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .offset(y = 20.dp),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.Center
      ) {
         SquareButton(
            text = "Message your field agent",
            onClick = { onNavigateToMessagePassing("The Control Desk") },
         )

         Spacer(modifier = Modifier.width(32.dp))

         SquareButton(
            text = "Learn Morse Code",
            onClick = { onNavigateToLearner("Morse Code Teacher") },
         )
      }
   } else {
      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         SquareButton(
            text = "Message your field agent",
            onClick = { onNavigateToMessagePassing("The Control Desk") },
         )

         Spacer(modifier = Modifier.height(16.dp))

         SquareButton(
            text = "Learn Morse Code",
            onClick = { onNavigateToLearner("Morse Code Teacher") }
         )

      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorseCoderAppBar(
   title: String,
   modifier: Modifier = Modifier,
   canNavigateBack: Boolean = false,
   onUpClick: () -> Unit = { },
) {
   TopAppBar(
      title = { Text(title, color = MaterialTheme.colorScheme.onSecondary) },
      colors = TopAppBarDefaults.topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
      modifier = modifier,
      navigationIcon = {
         if (canNavigateBack) {
            IconButton(onClick = onUpClick) {
               Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back",
                  tint = MaterialTheme.colorScheme.onSecondary)
            }
         }
      }
   )
}