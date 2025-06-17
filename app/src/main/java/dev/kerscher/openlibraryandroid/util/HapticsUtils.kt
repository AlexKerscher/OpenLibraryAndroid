package dev.kerscher.openlibraryandroid.util

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

fun performHapticFeedback(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    vibrator?.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
}