package com.asociateapp.components.progress.step

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.asociateapp.components.R

class StepStyler(private val context: Context) {

    internal var roadsAvailable = false
    internal var stepSize = context.resources.getDimensionPixelSize(StepDefaultValues.stepSize)
    internal var idleStepBackground: Drawable? =  ContextCompat.getDrawable(context, StepDefaultValues.idleStepBackground)
    internal var completedStepBackground: Drawable? = ContextCompat.getDrawable(context, StepDefaultValues.completedStepBackground)
    internal var completedStepIcon: Drawable? = ContextCompat.getDrawable(context, StepDefaultValues.completedStepIcon)

    internal fun drawStep(state: StepState, icon: ImageView, animate: Boolean) {
        buildIconParams(icon)
        when(state) {
            StepState.COMPLETED -> {
                drawCompletedStep(icon, animate)
            }
            StepState.IDLE -> {
                drawIdleStep(icon, animate)
            }
        }
    }

    private fun buildIconParams(icon: ImageView) {
        val horizontalMargin = getIconHorizontalMargin()
        val verticalMargin = getIconVerticalMargin()
        icon.layoutParams = LinearLayout.LayoutParams(stepSize, stepSize).also {
            it.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
        }
    }

    private fun getIconVerticalMargin() = context.resources.getDimensionPixelSize(StepDefaultValues.iconDefaultMargin)

    private fun getIconHorizontalMargin() = if (roadsAvailable) {
        0
    } else {
        context.resources.getDimensionPixelSize(R.dimen.step_horizontal_margins)
    }

    private fun drawCompletedStep(icon: ImageView, animate: Boolean) {
        icon.run {
            setImageDrawable(completedStepIcon)
            background = completedStepBackground
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        animateOnCompleted(icon, animate)
    }

    private fun animateOnCompleted(icon: ImageView, animate: Boolean) {
        if (!animate) {
            return
        }
        icon.alpha = 0.5f
        icon.animate()
            .translationY(-10f)
            .alpha(1f)
            .setDuration(120)
            .withEndAction {
                icon.animate().translationY(0f).setDuration(120).start()
            }.start()
    }

    private fun drawIdleStep(icon: ImageView, animate: Boolean) {
        icon.setImageDrawable(null)
        icon.background = idleStepBackground
        animateOnIdle(icon, animate)
    }

    private fun animateOnIdle(icon: ImageView, animate: Boolean) {
        if (!animate) {
            return
        }
        icon.animate()
            .scaleY(0.8f)
            .scaleX(0.8f)
            .setDuration(120)
            .withEndAction {
                icon.animate().scaleX(1f).scaleY(1f).setDuration(120).start()
            }.start()
    }
}