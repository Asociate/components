package com.asociateapp.components.progress.road

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat

class RoadStyler(context: Context) {
    internal var roadIdleColor =  ColorStateList(arrayOf(intArrayOf(android.R.attr.state_enabled)),
        intArrayOf(ContextCompat.getColor(context, RoadDefaultValues.defaultIdleColor)))

    internal var roadProgressColor =  ColorStateList(arrayOf(intArrayOf(android.R.attr.state_enabled)),
        intArrayOf(ContextCompat.getColor(context, RoadDefaultValues.defaultProgressColor)))
}