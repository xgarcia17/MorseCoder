package com.zybooks.petadoption.ui

import android.content.res.Configuration
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.media.ToneGenerator
import android.os.Build
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.petadoption.R

@Composable
fun SquareButton(
    text: String,
    onClick: () -> Unit,
    fontSize: Int = 24,
    height: Int = 220,
    width: Int = 220
) {
    Box(
        modifier = Modifier
            .height(height.dp)
            .width(width.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = fontSize.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun KeyButton(
    text: String,
    onClick: () -> Unit,
    buttonWidth: Int = 70,
    buttonHeight: Int = 70,
    sendingMessage: Boolean
) {
    var color = MaterialTheme.colorScheme.primary
    if (sendingMessage) {
        color = MaterialTheme.colorScheme.error
    }

    Box(
        modifier = Modifier
            .height(buttonHeight.dp)
            .width(buttonWidth.dp)
            .clip(RoundedCornerShape(4.dp))
            .padding(4.dp)
            .fillMaxWidth()
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = MaterialTheme.colorScheme.onPrimary)
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
fun MorseCodeButton(
    onTapComplete: (duration: Long) -> Unit,
    modifier: Modifier = Modifier,
    initialButtonSize: Dp = 160.dp,
    shrinkFactor: Float = 0.99f,
    colorChange: Boolean = true
) {
    var pressStartTime by remember { mutableStateOf(0L) }
    var isPressed by remember { mutableStateOf(false) }

    val normalColor = MaterialTheme.colorScheme.primaryContainer
    val pressedColor = MaterialTheme.colorScheme.primary
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    LaunchedEffect(isPressed) {
        if (isPressed) {
            // Start the beep sound
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, R.raw.sound_long_beep) // Load beep sound
            }
            mediaPlayer?.start() // Play sound
        } else {
            mediaPlayer?.pause() // Stop sound when released
            mediaPlayer?.seekTo(0)
        }
    }

    val buttonSize by animateDpAsState(
        targetValue = if (isPressed) initialButtonSize * shrinkFactor else initialButtonSize,
        animationSpec = tween(durationMillis = 10)
    )

    val buttonColor by animateColorAsState(
        targetValue = if (isPressed && colorChange) pressedColor else normalColor,
        animationSpec = tween(durationMillis = 10)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(buttonSize)
            .clip(CircleShape)
            .background(buttonColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressStartTime = System.currentTimeMillis()
                        isPressed = true
                        tryAwaitRelease()
                        val pressDuration = System.currentTimeMillis() - pressStartTime
                        onTapComplete(pressDuration)
                        isPressed = false
                    }
                )
            }
    ) {
        Text(
            text = "Tap",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}