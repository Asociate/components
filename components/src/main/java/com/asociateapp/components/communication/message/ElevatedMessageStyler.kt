package com.asociateapp.components.communication.message

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.asociateapp.components.R

internal class ElevatedMessageStyler : MessageStyler {

    override fun apply(parent: CardView) {
        parent.apply {
            radius = context.resources.getDimensionPixelSize(R.dimen.message_radius).toFloat()
            cardElevation = context.resources.getDimensionPixelSize(R.dimen.message_elevation).toFloat()
            setCardBackgroundColor(ContextCompat.getColor(parent.context, R.color.white))
        }
    }

}