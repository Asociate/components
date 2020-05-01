package com.asociateapp.components.common

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout

class RowManager(private val baseTag: String) {

    private var rowCounter = 0

    fun findOn(parent: ViewGroup, positionOnParent: Int): LinearLayout {
        return parent.findViewWithTag(baseTag + positionOnParent)
    }

    fun buildOn(parent: ViewGroup) = create(parent.context).apply {
        this.tag = baseTag + rowCounter
        this.orientation = LinearLayout.HORIZONTAL
        this.gravity = Gravity.CENTER
        parent.addView(this)
        incrementRowCounter()
    }

    private fun incrementRowCounter() {
        rowCounter++
    }

    private fun create(context: Context) = LinearLayout(context)

}