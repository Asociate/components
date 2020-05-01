package com.asociateapp.components.communication.message

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.asociateapp.components.R

internal class MessageIcon(context: Context) : ImageView(context) {

    init {
        buildParams()
        setBackground(null)
        setScale()
        applyFilter(context)
    }

    fun setImage(drawable: Drawable) {
        setImageDrawable(drawable)
    }

    private fun buildParams() {
        val size = resources.getDimensionPixelSize(R.dimen.message_icon_size)
        this.layoutParams = LinearLayout.LayoutParams(size, size)
    }

    override fun setBackground(background: Drawable?) {
        super.setBackground(ContextCompat.getDrawable(context, R.drawable.background_message_icon))
    }

    private fun setScale() {
        this.scaleType = ScaleType.CENTER_INSIDE
    }

    private fun applyFilter(context: Context) {
        this.setColorFilter(ContextCompat.getColor(context, R.color.asociate_green))
    }
}