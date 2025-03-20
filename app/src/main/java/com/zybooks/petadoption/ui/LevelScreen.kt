package com.zybooks.petadoption.ui

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LevelScreen(
    levelId: Int,
    modifier: Modifier = Modifier,
    viewModel: LevelViewModel = viewModel(),
    onUpClick: () -> Unit = { }
) {
    val level = viewModel.getLevel(levelId)
    var tapDuration by remember { mutableLongStateOf(-1L) }
    var charIndex by remember { mutableIntStateOf(0) }
    var charTapIndex by remember { mutableIntStateOf(0) }
    var curCharCode by remember { mutableStateOf("") }
    curCharCode = viewModel.getCharCode(level.chars[charIndex]).toString()
    var tempChar by remember { mutableStateOf('-') }
    tempChar = curCharCode[charTapIndex]
    var errInput by remember { mutableStateOf(false) }

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
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(innerPadding).fillMaxWidth()
            ) {
                Text(
                    text = level.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                // display the current character to be processed
                Text(
                    text = "${level.chars[charIndex]}",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold
                )

                DisplayBarCode(
                    code = curCharCode,
                    curIndex = charTapIndex,
                    err = errInput
                )

                Text(text = getTapType(tapDuration))

                MorseCodeButton(
                    modifier = Modifier.padding(vertical = 200.dp),
                    onTapComplete = { duration ->
                    // if dot and matches 'S'
                    if (duration in 0..499 && tempChar == 'S') {
                        // move onto next part of same char
                        charTapIndex ++
                        errInput = false
                    } // if dash and matches 'L'
                    else if (duration >= 500 && tempChar == 'L') {
                        // move onto next part of same char
                        charTapIndex ++
                        errInput = false
                    } else {
                        charTapIndex = 0
                        errInput = true
                    }
                    tapDuration = duration
                    if (charTapIndex == curCharCode.length) { // finished with character
                        charIndex ++
                        charTapIndex = 0
                        curCharCode = viewModel.getCharCode(level.chars[charIndex]).toString()
                        tempChar = curCharCode[charTapIndex]
                        tapDuration = 0
                    }
                })
            }
    }
}

@Composable
fun DisplayBarCode(code: String, curIndex: Int, err: Boolean) {
    var count = 0
    var color = MaterialTheme.colorScheme.primary
    if (err) color = MaterialTheme.colorScheme.error

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        for (ch in code) {
            if (count >= curIndex) color = MaterialTheme.colorScheme.onPrimaryContainer
            if (ch == 'S') {
                MorseCodeDot(color)
            } else {
                MorseCodeDash(color)
            }
            count ++
        }
    }
}

@Composable
fun getTapType(duration: Long): String {
    if (duration <= 0) {
        return ""
    } else if (duration < 500) {
        return "Dot"
    }
    return "Dash"
}