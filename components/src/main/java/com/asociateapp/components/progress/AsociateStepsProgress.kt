package com.asociateapp.components.progress

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import com.asociateapp.components.R
import com.asociateapp.components.progress.road.HorizontalRoad
import com.asociateapp.components.progress.road.Road
import com.asociateapp.components.progress.road.RoadStyler
import com.asociateapp.components.progress.step.Step
import com.asociateapp.components.progress.step.StepStyler

class AsociateStepsProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var stepStyler = StepStyler(context)
    private var roadStyler = RoadStyler(context)

    init {
        initialize(attrs)
    }

    private var currentPosition = 0
    private var initialSetUpCompleted = false
    private var stepsCount = 0
    private var joinByRoads = false

    fun goToNextStep() {
        val nextPosition = currentPosition + 1
        if (nextPosition == childCount) {
            return
        }
        currentPosition = nextPosition
        markCurrentStepAsCompleted(true)
    }

    fun returnToPreviousStep() {
        val previousPosition = currentPosition - 1
        if (previousPosition < 0) {
            return
        }
        markCurrentStepAsIdle()
    }

    private fun initialize(attrs: AttributeSet?) {
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
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgress)
            buildStepStylerAttrs(typedArray)
            buildRoadStylerAttrs(typedArray)
            stepsCount = typedArray.getInteger(R.styleable.HorizontalProgress_stepsCount, 0)
            joinByRoads = typedArray.getBoolean(R.styleable.HorizontalProgress_addRoads, false)
            stepStyler.roadsAvailable = joinByRoads
            typedArray.recycle()
        }
    }

    private fun buildStepStylerAttrs(typedArray: TypedArray) {
        stepStyler.stepSize = typedArray.getDimension(R.styleable.HorizontalProgress_stepSize, stepStyler.stepSize.toFloat()).toInt()
        typedArray.getDrawable(R.styleable.HorizontalProgress_stepBackgroundCompleted)?.let {
            stepStyler.completedStepBackground = it
        }
        typedArray.getDrawable(R.styleable.HorizontalProgress_stepBackgroundIdle)?.let {
            stepStyler.idleStepBackground = it
        }
        typedArray.getDrawable(R.styleable.HorizontalProgress_stepIcon)?.let {
            stepStyler.completedStepIcon = it
        }
    }

    private fun buildRoadStylerAttrs(typedArray: TypedArray) {
        typedArray.getColorStateList(R.styleable.HorizontalProgress_roadProgressColor)?.let {
            roadStyler.roadProgressColor = it
        }
        typedArray.getColorStateList(R.styleable.HorizontalProgress_roadIdleColor)?.let {
            roadStyler.roadIdleColor = it
        }
    }

    private fun addChildren() {
        if (!initialSetUpCompleted) {
            var childCount = 0
            while (childCount < stepsCount) {
                val step = Step(context, stepStyler = stepStyler)
                addView(step)

                if (childCount + 1 != stepsCount && joinByRoads) {
                    val road = HorizontalRoad(context)
                    road.setRoadStyler(roadStyler)
                    addView(road)
                }
                childCount += 1
            }
            markFirstStepAsCompleted()
        }
    }

    private fun markFirstStepAsCompleted() {
        if (childCount == 0) {
            Log.e(TAG, "No steps added")
            return
        }
        if (!initialSetUpCompleted) {
            markCurrentStepAsCompleted(false)
            initialSetUpCompleted = true
        }
    }

    private fun markCurrentStepAsCompleted(animate: Boolean) {
        val road = getChildAt(currentPosition) as? Road
        if (road == null) {
            val step = getChildAt(currentPosition) as Step
            step.markAsCompleted(animate)
            moveOnNextRoad()
            return
        }
        currentPosition += 1
        road.moveNext {
            markCurrentStepAsCompleted(true)
        }
    }

    private fun moveOnNextRoad() {
        val road = getChildAt(currentPosition + 1) as? Road
        if (road == null) {
            Log.i(TAG, "No road added in the next step")
            return
        }
        road.moveNext()
    }

    private fun markCurrentStepAsIdle() {
        goBackToPreviousRoadState(currentPosition + 1) {
            val step = getChildAt(currentPosition) as Step
            step.markAsIdle(true)
            currentPosition -= 1

            val roadUpdated = goBackToPreviousRoadState(currentPosition)
            if (roadUpdated) {
                currentPosition -= 1
            }
        }
    }

    private fun goBackToPreviousRoadState(position: Int, onRoadUpdated: () -> Unit = {}): Boolean {
        val road = getChildAt(position) as? Road
        if (road == null) {
            Log.i(TAG, "No road added in the next step")
            onRoadUpdated()
            return false
        }
        road.backToPrevious(onRoadUpdated)
        return true
    }

    companion object {
        private const val TAG = "HorizontalProgress"
    }
}