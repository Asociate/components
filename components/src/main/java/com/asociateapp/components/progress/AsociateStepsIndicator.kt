package com.asociateapp.components.progress

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.asociateapp.components.R
import com.asociateapp.components.progress.step.OnStepClickListener
import com.asociateapp.components.progress.step.Step
import com.asociateapp.components.progress.step.StepStyler

class AsociateStepsIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), StepsGrouper, OnStepClickListener {

    override fun goToNext() {
        if (selectedPosition + 1 == childCount) {
            return
        }
        onStepClick(selectedPosition + 1)
    }

    override fun returnToPrevious() {
        if (selectedPosition == 0) {
            return
        }
        onStepClick(selectedPosition - 1)
    }

    override fun onStepClick(position: Int) {
        if (position == selectedPosition) {
            indicatorClickListener?.onPositionReselected(position)
            return
        }
        markStepAsIdle(selectedPosition)
        indicatorClickListener?.onPositionUnselected(selectedPosition)

        markStepAsSelected(position)
        indicatorClickListener?.onNewPositionClick(position)

        updateSelectedPosition(position)
    }

    fun disable() {
        setAvailability(false)
    }

    fun enable() {
        setAvailability(true)
    }

    private var indicatorClickListener: OnIndicatorClickListener? = null

    fun setIndicatorClickListener(listener: OnIndicatorClickListener) {
        this.indicatorClickListener = listener
    }

    private var stepStyler = StepStyler(context)

    private var selectedPosition = 0
    private var initialSetUpCompleted = false
    private var stepsCount = 0

    init {
        setBaseDesign()
        initializeAttributes(attrs)
        addChildren()
    }

    private fun setBaseDesign() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    private fun initializeAttributes(attrs: AttributeSet?) {
        attrs?.run {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AsociateStepsIndicator)
            buildStepStylerAttrs(typedArray)
            stepsCount = typedArray.getInteger(R.styleable.AsociateStepsIndicator_stepsCount, 0)
            typedArray.recycle()
        }
        this.stepStyler.roadsAvailable = false
    }

    private fun buildStepStylerAttrs(typedArray: TypedArray) {
        this.stepStyler.stepSize = typedArray.getDimension(R.styleable.AsociateStepsIndicator_stepSize, stepStyler.stepSize.toFloat()).toInt()
        typedArray.getDrawable(R.styleable.AsociateStepsIndicator_stepBackgroundCompleted)?.let {
            this.stepStyler.completedStepBackground = it
        }
        typedArray.getDrawable(R.styleable.AsociateStepsIndicator_stepBackgroundIdle)?.let {
            this.stepStyler.idleStepBackground = it
        }
        typedArray.getDrawable(R.styleable.AsociateStepsIndicator_stepIcon)?.let {
            this.stepStyler.completedStepIcon = it
        }
    }

    private fun addChildren() {
        if (!initialSetUpCompleted) {
            var childCount = 0
            while (childCount < this.stepsCount) {
                val step = Step(context, stepStyler = this.stepStyler).apply {
                    tag = "Step$childCount"
                    assignPosition(childCount)
                    setOnStepClickListener(this@AsociateStepsIndicator)
                }
                addView(step)
                childCount += 1
            }
            markFirstStepAsSelected()
        }
    }

    private fun markFirstStepAsSelected() {
        if (childCount == 0) {
            return
        }
        if (!this.initialSetUpCompleted) {
            markStepAsSelected(0)
            this.initialSetUpCompleted = true
        }
    }

    private fun markStepAsSelected(position: Int) {
        (getChildAt(position) as Step).markAsCompleted(true)
    }

    private fun markStepAsIdle(position: Int) {
        (getChildAt(position) as Step).markAsIdle(true)
    }

    private fun updateSelectedPosition(newPosition: Int) {
        this.selectedPosition = newPosition
    }

    private fun setAvailability(enable: Boolean) {
        var childPosition = 0
        while(childPosition < childCount) {
            val child = getChildAt(childPosition)
            child.isEnabled = enable
            childPosition += 1
        }
    }
}