package com.zybooks.petadoption.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zybooks.petadoption.data.Pet
import com.zybooks.petadoption.data.MorseCoderDataSource
import com.zybooks.petadoption.data.PetGender
import com.zybooks.petadoption.ui.theme.MorseCoderTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MorseCoderApp() {
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
                  // Navigate to the ListScreen with the appropriate title
                  navController.navigate("list_screen/$title")
               },
               onNavigateToLearner = { title ->
                  // Navigate to the ListScreen with the appropriate title
                  navController.navigate("list_screen/$title")
               }
            )
         }
      }

      composable("list_screen/{title}") { backstackEntry ->
         val title = backstackEntry.arguments?.getString("title") ?: "Default Title"

         ListScreen(
            title = title, // Pass the title to ListScreen
            onImageClick = { pet ->
               navController.navigate("detail_screen/${pet.id}")
            },
            onUpClick = {
               // Navigate back to the "start_screen"
               navController.navigateUp()  // This will navigate back to the start screen
            }
         )
      }

      composable("detail_screen/{petId}") { backstackEntry ->
         val petId = backstackEntry.arguments?.getString("petId")?.toInt() ?: 0
         DetailScreen(
            petId = petId,
            onAdoptClick = {
               navController.navigate("adopt_screen/$petId")
            },
            onUpClick = {
               navController.navigateUp()
            }
         )
      }

      composable("adopt_screen/{petId}") { backstackEntry ->
         val petId = backstackEntry.arguments?.getString("petId")?.toInt() ?: 0
         AdoptScreen(
            petId = petId,
            onUpClick = {
               navController.navigateUp()
            }
         )
      }
   }
}

@Composable
fun SquareButton(
   text: String,
   onClick: () -> Unit
) {
   Box(
      modifier = Modifier
         .size(220.dp)
         .clip(RoundedCornerShape(16.dp))
         .background(MaterialTheme.colorScheme.primary)
         .clickable { onClick() },
      contentAlignment = Alignment.Center
   ) {
      Text(text, color = MaterialTheme.colorScheme.onPrimary)
   }
}


@Composable
fun StartScreen(
   onNavigateToMessagePassing: (String) -> Unit,
   onNavigateToLearner: (String) -> Unit
) {
   Column(
      modifier = Modifier
         .fillMaxSize()
         .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
   ) {
      SquareButton(
         text = "Message your field agent",
         onClick = { onNavigateToMessagePassing("The Control Desk") }
      )

      Spacer(modifier = Modifier.height(16.dp))

      SquareButton(
         text = "Learn Morse Code",
         onClick = { onNavigateToLearner("Morse Code Teacher") }
      )
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

@Composable
fun ListScreen(
   title: String,
   onImageClick: (Pet) -> Unit,
   modifier: Modifier = Modifier,
   viewModel: ListViewModel = viewModel(),
   onUpClick: () -> Unit = { }
) {
   Scaffold(
      topBar = {
         MorseCoderAppBar(
            title = title,
            canNavigateBack = true,
            onUpClick = onUpClick
         )
      }
   ) { innerPadding ->
      LazyVerticalGrid(
         columns = GridCells.Adaptive(minSize = 128.dp),
         contentPadding = PaddingValues(0.dp),
         modifier = modifier.padding(innerPadding)
      ) {
         items(viewModel.petList) { pet ->
            Image(
               painter = painterResource(id = pet.imageId),
               contentDescription = "${pet.type} ${pet.gender}",
               modifier = Modifier.clickable(
                  onClick = { onImageClick(pet) },
                  onClickLabel = "Select the pet"
               )
            )
         }
      }
   }
}

@Preview
@Composable
fun PreviewListScreen() {
   MorseCoderTheme {
      ListScreen(
         title = "test title",
         onImageClick = { }
      )
   }
}

@Composable
fun DetailScreen(
   petId: Int,
   onAdoptClick: () -> Unit,
   modifier: Modifier = Modifier,
   viewModel: DetailViewModel = viewModel(),
   onUpClick: () -> Unit = { }
) {
   val pet = viewModel.getPet(petId)
   val gender = if (pet.gender == PetGender.MALE) "Male" else "Female"

   Scaffold(
      topBar = {
         MorseCoderAppBar(
            title = "Details",
            canNavigateBack = true,
            onUpClick = onUpClick
         )
      }
   ) { innerPadding ->
      Column(
         modifier = modifier.padding(innerPadding)
      ) {
         Image(
            painter = painterResource(pet.imageId),
            contentDescription = pet.name,
            contentScale = ContentScale.FillWidth
         )
         Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = modifier.padding(6.dp)
         ) {
            Row(
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically,
               modifier = modifier.fillMaxWidth()
            ) {
               Text(
                  text = pet.name,
                  style = MaterialTheme.typography.headlineMedium
               )
               Button(onClick = onAdoptClick) {
                  Text("Adopt Me!")
               }
            }
            Text(
               text = "Gender: $gender",
               style = MaterialTheme.typography.bodyLarge
            )
            Text(
               text = "Age: ${pet.age}",
               style = MaterialTheme.typography.bodyLarge
            )
            Text(
               text = pet.description,
               style = MaterialTheme.typography.bodyMedium
            )
         }
      }
   }
}

@Preview
@Composable
fun PreviewDetailScreen() {
   val pet = MorseCoderDataSource().loadPets()[0]
   MorseCoderTheme {
      DetailScreen(
         petId = pet.id,
         onAdoptClick = { }
      )
   }
}

@Composable
fun AdoptScreen(
   petId: Int,
   modifier: Modifier = Modifier,
   viewModel: AdoptViewModel = viewModel(),
   onUpClick: () -> Unit = { }
) {
   val pet = viewModel.getPet(petId)
   Scaffold(
      topBar = {
         MorseCoderAppBar(
            title = "Thank You!",
            canNavigateBack = true,
            onUpClick = onUpClick
         )
      }
   ) { innerPadding ->
      Column(
         modifier = modifier.padding(innerPadding)
      ) {
         Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
               painter = painterResource(pet.imageId),
               contentDescription = pet.name,
               modifier = modifier.size(150.dp)
            )
            Text(
               text = "Thank you for adopting ${pet.name}!",
               modifier = modifier.padding(horizontal = 28.dp),
               textAlign = TextAlign.Center,
               style = MaterialTheme.typography.headlineLarge,
            )
         }
         Text(
            text = "Please pick up your new family member during business hours.",
            modifier = modifier.padding(6.dp),
         )
         Button(
            onClick = { },
            modifier = modifier.padding(6.dp)
         ) {
            Icon(Icons.Default.Share, null)
            Text("Share", modifier = modifier.padding(start = 8.dp))
         }
      }
   }
}

@Preview
@Composable
fun PreviewAdoptScreen() {
   val pet = MorseCoderDataSource().loadPets()[0]
   MorseCoderTheme {
      AdoptScreen(pet.id)
   }
}