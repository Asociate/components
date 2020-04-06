package com.asociateapp.components.progress

interface OnIndicatorClickListener {
    fun onPositionReselected(position: Int)
    fun onPositionUnselected(position: Int)
    fun onNewPositionClick(position: Int)
}