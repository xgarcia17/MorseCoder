package com.zybooks.petadoption.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.zybooks.petadoption.data.Level

@Composable
fun LearnerScreen(
    title: String,
    onButtonClick: (Level) -> Unit,
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
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier.padding(innerPadding)
        ) {
            items(viewModel.levelList) { level ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SquareButton(
                        text = "Level ${level.id}:\n${level.title}",
                        onClick = { onButtonClick(level) },
                        height = 150
                    )
                }
            }
        }
    }
}