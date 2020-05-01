package com.asociateapp.components.communication.message

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.asociateapp.components.R

internal class FlatMessageStyler : MessageStyler {

    override fun apply(parent: CardView) {
        parent.apply {
            cardElevation = FLAT_ELEVATION
            radius = context.resources.getDimensionPixelSize(R.dimen.message_radius).toFloat()
            setCardBackgroundColor(ContextCompat.getColor(parent.context, R.color.light_gray))
        }

    }

    companion object {
        private const val FLAT_ELEVATION = 0F
    }
}