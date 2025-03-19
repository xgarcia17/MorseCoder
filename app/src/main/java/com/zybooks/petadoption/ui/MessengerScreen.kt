package com.zybooks.petadoption.ui

import android.content.res.Configuration
import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MessengerScreen(
    title: String,
    modifier: Modifier = Modifier,
    onUpClick: () -> Unit = { }
) {
    val context = LocalContext.current
    val charLimit = 30

    // Declare state to hold the message text
    var message by remember { mutableStateOf("") }
    var charCount by remember { mutableStateOf(0) }
    var sendingMessage by remember { mutableStateOf(false) }

    val config = LocalConfiguration.current
    val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE

    val audioViewModel = remember { AudioViewModel() }
    LaunchedEffect(Unit) {
        audioViewModel.initializeSoundPool(context)
    }

    Scaffold(
        topBar = {
            MorseCoderAppBar(
                title = title,
                canNavigateBack = true,
                onUpClick = onUpClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            if (!sendingMessage) {
                // Display the current message at the top
                MessageInBox("$message|")
                if (!isLandscape) {
                    Text(
                        text = "Character Count: $charCount/$charLimit",
                        modifier = Modifier.padding(6.dp)
                    )
                }
            } else {
                Text(
                    text = "TRANSMITTING MESSAGE",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(y = 30.dp),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 24.sp
                )
            }

            Keyboard(
                onButtonClick = { buttonText ->
                    when (buttonText) {
                        // backspace
                        "Delete" -> {
                            if (message.isNotEmpty()) {
                                if (message.last() != ' ') {
                                    charCount--
                                }
                                message = message.dropLast(1)
                            }
                        }
                        // space
                        "Space" -> {
                            if (charCount != 0 && message.last() != ' ') {
                                message += ' '
                            }
                        }
                        // submit message
                        "Done" -> {
                            CoroutineScope(Dispatchers.Main).launch {
                                sendingMessage = true
                                charCount = 0
                                // Wait for the audio to finish playing
                                val job = audioViewModel.playStringAudio(message)
                                job.join() // suspend until the audio finishes
                                sendingMessage = false
                                message = ""
                            }
                        }

                        else -> {
                            if (charCount < 30) {
                                message += buttonText
                                charCount++
                            }
                        }
                    }
                },
                sendingMessage = sendingMessage
            )
        }
    }
}

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit,
    sendingMessage: Boolean
) {
    val config = LocalConfiguration.current
    val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE
    var keyboardIndex by remember { mutableStateOf(0) }

    val keyboardRowsPortrait = listOf(
        listOf("1", "2", "3", "4", "5"),
        listOf("6", "7", "8", "9", "0"),
        listOf("A", "B", "C", "D", "E"),
        listOf("F", "G", "H", "I", "J"),
        listOf("K", "L", "M", "N", "O"),
        listOf("P", "Q", "R", "S", "T"),
        listOf("U", "V", "W", "X", "Y"),
        listOf("Z", "Space", "Delete"),
        listOf("Done")
    )
    val keyboardRowsLandscape = listOf(
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
        listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"),
        listOf( "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"),
        listOf("Delete", "Space", "Done")
    )
    val keyboardRows = listOf(keyboardRowsPortrait, keyboardRowsLandscape)

    // Define button widths based on content
    val defaultWidth = 70
    var buttonHeight = 70
    val buttonWidths = mapOf(
        "Space" to 120,
        "Delete" to 120,
        "Done" to 180
    ) // Create the keyboard UI
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLandscape) {
            keyboardIndex = 1
            buttonHeight = 50
        }
        // Loop through each row and display the buttons
        keyboardRows[keyboardIndex].forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEach { buttonText ->
                    val buttonWidth = buttonWidths[buttonText] ?: defaultWidth
                    KeyButton(
                        onClick = { onButtonClick(buttonText) },
                        text = buttonText,
                        buttonWidth = buttonWidth,
                        buttonHeight = buttonHeight,
                        sendingMessage = sendingMessage
                    )
                }
            }
        }
    }
}

@Composable
fun MessageInBox(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(2.dp, MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(2.dp))
            .padding(16.dp)
    ) {
        Text(
            text = message,
        )
    }
}
