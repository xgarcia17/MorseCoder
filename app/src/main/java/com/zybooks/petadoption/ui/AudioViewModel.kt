package com.zybooks.petadoption.ui

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.ViewModel
import com.zybooks.petadoption.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioViewModel : ViewModel() {

    private lateinit var soundPool: SoundPool
    private val soundMap = mutableMapOf<Char, Int>()

    // init the sound pool
    fun initializeSoundPool(context: Context) {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(audioAttributes)
            .build()

        loadAudioFiles(context)
    }

    // load the audio files when opening the view
    private fun loadAudioFiles(context: Context) {
        val charToFileMap = mapOf(
            'A' to R.raw.sound_a,
            'B' to R.raw.sound_b,
            'C' to R.raw.sound_c,
            'D' to R.raw.sound_d,
            'E' to R.raw.sound_e,
            'F' to R.raw.sound_f,
            'G' to R.raw.sound_g,
            'H' to R.raw.sound_h,
            'I' to R.raw.sound_i,
            'J' to R.raw.sound_j,
            'K' to R.raw.sound_k,
            'L' to R.raw.sound_l,
            'M' to R.raw.sound_m,
            'N' to R.raw.sound_n,
            'O' to R.raw.sound_o,
            'P' to R.raw.sound_p,
            'Q' to R.raw.sound_q,
            'R' to R.raw.sound_r,
            'S' to R.raw.sound_s,
            'T' to R.raw.sound_t,
            'U' to R.raw.sound_u,
            'V' to R.raw.sound_v,
            'W' to R.raw.sound_w,
            'X' to R.raw.sound_x,
            'Y' to R.raw.sound_y,
            'Z' to R.raw.sound_z,
            '0' to R.raw.sound_0,
            '1' to R.raw.sound_1,
            '2' to R.raw.sound_2,
            '3' to R.raw.sound_3,
            '4' to R.raw.sound_4,
            '5' to R.raw.sound_5,
            '6' to R.raw.sound_6,
            '7' to R.raw.sound_7,
            '8' to R.raw.sound_8,
            '9' to R.raw.sound_9
        )

        for ((char, resID) in charToFileMap) {
            val soundID = soundPool.load(context, resID, 1)
            soundMap[char] = soundID
        }
    }

    fun playStringAudio(str: String): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            for (ch in str) {
                if (ch != ' ') {
                    soundMap[ch]?.let { soundId ->
                        soundPool.play(soundId, 1f, 1f, 0, 0, 0.8f)
                        delay(1200) // Delay between sounds
                    }
                } else {
                    delay(1000) // Larger delay for space or unknown character
                }
            }
        }
    }


    // Release resources when no longer needed
    override fun onCleared() {
        super.onCleared()
        soundPool.release()
    }
}