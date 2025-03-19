package com.zybooks.petadoption.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
//@Composable
//fun isInLandscape(): Boolean {
//    val config = LocalConfiguration.current
//    return config.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
//}
//