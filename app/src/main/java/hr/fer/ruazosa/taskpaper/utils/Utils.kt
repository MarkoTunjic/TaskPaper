package hr.fer.ruazosa.taskpaper.utils

import android.content.Context

class Utils {
    companion object{
        fun getPixelsFromDp(paddingDp: Int, context: Context): Int {
            var density: Float = context.resources.displayMetrics.density
            return (paddingDp * density).toInt()
        }
    }
}