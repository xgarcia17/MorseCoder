package com.zybooks.petadoption.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LevelScreen(
    levelId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(),
    onUpClick: () -> Unit = { }
) {
    val level = viewModel.getLevel(levelId)
    var tapDuration by remember { mutableStateOf(0L) }

    Scaffold(
        topBar = {
            MorseCoderAppBar(
                title = "Level ${level.id}",
                canNavigateBack = true,
                onUpClick = onUpClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = modifier.padding(6.dp).fillMaxWidth()
            ) {
                Text(
                    text = level.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(text = level.chars.toString())

                Text(text = "Tap Duration: $tapDuration")

                Row(Modifier.align(Alignment.CenterHorizontally)) {
                    MorseCodeButton(onTapComplete = { duration ->
                        tapDuration = duration
                    })
                }
            }
        }
    }
}