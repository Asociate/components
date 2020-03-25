package com.asociateapp.components.progress.road

interface Road {
    fun moveNext(onRoadMovementFinished: () -> Unit = {})
    fun backToPrevious(onRoadMovementFinished: () -> Unit = {})
}