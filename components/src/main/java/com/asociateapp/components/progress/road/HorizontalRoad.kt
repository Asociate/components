package com.asociateapp.components.progress.road

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.widget.ProgressBar

class HorizontalRoad @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.progressBarStyleHorizontal
) : ProgressBar(context, attrs, defStyleAttr), Road {

    init {
        max = 100
        secondaryProgress = 100
    }

    private var state = RoadState.IDLE

    fun setRoadStyler(roadStyler: RoadStyler) {
        secondaryProgressTintList = roadStyler.roadIdleColor
        progressTintList = roadStyler.roadProgressColor
    }

    override fun moveNext(onRoadMovementFinished: () -> Unit) {
        incrementState()
        paintIncrementing(onRoadMovementFinished)
    }

    override fun backToPrevious(onRoadMovementFinished: () -> Unit) {
        decrementState()
        paintDecrementing(onRoadMovementFinished)
    }

    private fun incrementState() {
        when(state) {
            RoadState.IDLE -> {
                state = RoadState.PARTIAL
            }
            RoadState.PARTIAL -> {
                state = RoadState.COMPLETE
            }
            RoadState.COMPLETE -> {
                // end has been reached, there is no higher state
            }
        }
    }

    private fun paintIncrementing(onRoadMovementFinished: () -> Unit) {
        when(state) {
            RoadState.IDLE -> {}
            RoadState.PARTIAL -> {
                paintLineAnimated(0f, 50f, onRoadMovementFinished)
            }
            RoadState.COMPLETE -> {
                paintLineAnimated(50f, 100f, onRoadMovementFinished)
            }
        }
    }

    private fun decrementState() {
        when(state) {
            RoadState.IDLE -> {
                // nothing to do, there is no lower state
            }
            RoadState.PARTIAL -> {
                state = RoadState.IDLE
            }
            RoadState.COMPLETE -> {
                state = RoadState.PARTIAL
            }
        }
    }

    private fun paintDecrementing(onRoadMovementFinished: () -> Unit) {
        when(state) {
            RoadState.IDLE -> {
                paintLineAnimated(50f, 0f, onRoadMovementFinished)
            }
            RoadState.PARTIAL -> {
                paintLineAnimated(100f, 50f, onRoadMovementFinished)
            }
            RoadState.COMPLETE -> {}
        }
    }

    private fun paintLineAnimated(from: Float, to: Float, onRoadMovementFinished: () -> Unit) {
        val roadAnimator = RoadAnimator(this, from, to)
        roadAnimator.duration = 250
        roadAnimator.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                onRoadMovementFinished()
            }
            override fun onAnimationStart(animation: Animation?) {}
        })
        startAnimation(roadAnimator)
    }
}