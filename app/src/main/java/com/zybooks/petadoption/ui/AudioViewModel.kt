package com.zybooks.petadoption.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioViewModel : ViewModel() {

    // retrieve the audio file for a given character
    fun getAudioFile(context: Context, ch: Char): MediaPlayer? {
        // I would like to dynamically lookup the audio files with getIdentifier(), but
        // that function is not recommended as it is much slower than looking up
        // an audio file, such as R.raw.sound_a, directly
        val resourceID = when (ch) {
            'A' -> R.raw.sound_a
            'B' -> R.raw.sound_b
            'C' -> R.raw.sound_c
            'D' -> R.raw.sound_d
            'E' -> R.raw.sound_e
            'F' -> R.raw.sound_f
            'G' -> R.raw.sound_g
            'H' -> R.raw.sound_h
            'I' -> R.raw.sound_i
            'J' -> R.raw.sound_j
            'K' -> R.raw.sound_k
            'L' -> R.raw.sound_l
            'M' -> R.raw.sound_m
            'N' -> R.raw.sound_n
            'O' -> R.raw.sound_o
            'P' -> R.raw.sound_p
            'Q' -> R.raw.sound_q
            'R' -> R.raw.sound_r
            'S' -> R.raw.sound_s
            'T' -> R.raw.sound_t
            'U' -> R.raw.sound_u
            'V' -> R.raw.sound_v
            'W' -> R.raw.sound_w
            'X' -> R.raw.sound_x
            'Y' -> R.raw.sound_y
            'Z' -> R.raw.sound_z
            '0' -> R.raw.sound_0
            '1' -> R.raw.sound_1
            '2' -> R.raw.sound_2
            '3' -> R.raw.sound_3
            '4' -> R.raw.sound_4
            '5' -> R.raw.sound_5
            '6' -> R.raw.sound_6
            '7' -> R.raw.sound_7
            '8' -> R.raw.sound_8
            '9' -> R.raw.sound_9
            else -> 0
        }
        if (resourceID != 0) {
            return MediaPlayer.create(context, resourceID)
        } else {
            return null
        }
    }

    // play the Morse Code translation of a given string
    fun playStringAudio(context: Context, str: String) {
        CoroutineScope(Dispatchers.IO).launch {
            for (ch in str) {
                val mediaPlayer = getAudioFile(context, ch)
                if (mediaPlayer != null) {
                    mediaPlayer.let {
                        it.start()
                        it.setOnCompletionListener {
                            Handler(Looper.getMainLooper()).postDelayed({
                                it.release()
                            }, 500)
                        }
                    }
                    delay(500)
                } else { // case of space or unknown character
                    delay(1000)
                }
            }
        }
    }
}