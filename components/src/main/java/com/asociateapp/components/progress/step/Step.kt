package com.asociateapp.components.progress.step

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.asociateapp.components.R
import kotlinx.android.synthetic.main.step.view.*

internal class Step @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val stepStyler: StepStyler = StepStyler(context)
) : LinearLayout(context, attrs, defStyleAttr) {

    private var state = StepState.IDLE

    init {
        inflateLayout()
        setBaseStepAttributes()
        drawStep(step_icon, false)
        setClickListener()
    }

    private var position: Int = 0
    private var stepClickListener: OnStepClickListener? = null

    internal fun assignPosition(position: Int) {
        this.position = position
    }

    internal fun setOnStepClickListener(listener: OnStepClickListener) {
        this.stepClickListener = listener
    }

    internal fun markAsCompleted(animate: Boolean) {
        if (isCompleted()) {
            return
        }
        this.state = StepState.COMPLETED
        drawStep(step_icon, animate)
    }

    internal fun markAsIdle(animate: Boolean) {
        if(!isCompleted()) {
            return
        }
        this.state = StepState.IDLE
        drawStep(step_icon, animate)
    }

    private fun inflateLayout() {
        LayoutInflater.from(context).inflate(R.layout.step, this, true)
    }

    private fun setBaseStepAttributes() {
        orientation = VERTICAL
        gravity = Gravity.CENTER
    }

    private fun drawStep(icon: ImageView, animate: Boolean) {
        stepStyler.drawStep(state, icon, animate)
    }

    private fun isCompleted() = this.state == StepState.COMPLETED

    private fun setClickListener() {
        this.setOnClickListener {
            stepClickListener?.onStepClick(position)
        }
    }
}