package com.zybooks.petadoption.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MessengerScreen(
    title: String,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel(),
    onUpClick: () -> Unit = { }
) {
    val charLimit = 30
    // Declare state to hold the message text
    var message by remember { mutableStateOf("") }
    var charCount by remember { mutableStateOf(0) }

    // Custom keyboard buttons (characters to be displayed)
    val keyboardButtons = listOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
        "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
        "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " "
    )

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
            // Display the current message at the top
            MessageInBox("$message|")

            Text(
                text = "Character Count: $charCount/$charLimit",
                modifier = Modifier.padding(6.dp)
            )

            Keyboard(
                onButtonClick = { buttonText ->
                    when (buttonText) {
                        // backspace
                        "<-" -> {
                            if (message.isNotEmpty()) {
                                if (message.last() != ' ') {
                                    charCount --
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
                        // TODO
                       }
                        else -> {
                            if (charCount < 30) {
                                message += buttonText
                                charCount ++
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit
) {
    val keyboardRows = listOf(
        listOf("1", "2", "3", "4", "5"),  // Row 1: Numbers
        listOf("6", "7", "8", "9", "0"),  // Row 2: Numbers
        listOf("A", "B", "C", "D", "E"),  // Row 3: Letters
        listOf("F", "G", "H", "I", "J"),  // Row 4: Letters
        listOf("K", "L", "M", "N", "O"),  // Row 5: Letters
        listOf("P", "Q", "R", "S", "T"),  // Row 6: Letters
        listOf("U", "V", "W", "X", "Y"),  // Row 7: Letters
        listOf("Z", "Space", "<-"), // Row 8: Special Keys
        listOf("Done") // Row 9: Finish message
    )

    // Create the keyboard UI
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Loop through each row and display the buttons
        keyboardRows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                row.forEach { buttonText ->
                    var buttonWidth = 70
                    if (buttonText == "Space") {
                        buttonWidth = 120
                    } else if (buttonText == "Done") {
                        buttonWidth = 180
                    }
                    KeyButton(
                        onClick = { onButtonClick(buttonText) },
                        text = buttonText,
                        buttonWidth = buttonWidth
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
