package com.asociateapp.components.text

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.asociateapp.components.R
import kotlinx.android.synthetic.main.bullet_text.view.*

class AsociateBulletText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflateView()
        initializeParams(attrs)
        applyParams()
        orientation = HORIZONTAL
    }

    private fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.bullet_text, this, true)
    }

    private var bulletText: String = ""

    /**
     * The default value of this attributes will be taken
     * from the xml, so if null, don't use it
     */
    private var bulletTextSize: Float? = null
    private var bulletTextColor: Int? = null
    private var bulletColor: Int? = null
    private var bulletSize: Int? = null

    private fun initializeParams(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AsociateBulletText)
            initializeBulletAttributes(typedArray)
            initializeTextAttributes(typedArray)
            typedArray.recycle()
        }
    }

    private fun initializeBulletAttributes(typedArray: TypedArray) {
        if (typedArray.hasValue(R.styleable.AsociateBulletText_bulletColor)) {
            bulletColor = typedArray.getColor(
                R.styleable.AsociateBulletText_bulletColor,
                ContextCompat.getColor(context, R.color.asociate_green)
            )
        }
        if (typedArray.hasValue(R.styleable.AsociateBulletText_bulletSize)) {
            bulletSize = typedArray.getDimension(R.styleable.AsociateBulletText_bulletSize,
                context.resources.getDimensionPixelSize(R.dimen.bullet_size).toFloat()).toInt()
        }
    }

    private fun initializeTextAttributes(typedArray: TypedArray) {
        bulletText = typedArray.getString(R.styleable.AsociateBulletText_bulletText) ?: ""

        if (typedArray.hasValue(R.styleable.AsociateBulletText_bulletTextSize)) {
            bulletTextSize = typedArray.getDimension(R.styleable.AsociateBulletText_bulletTextSize,
                context.resources.getDimensionPixelSize(R.dimen.bullet_text_size).toFloat())
        }
        if (typedArray.hasValue(R.styleable.AsociateBulletText_bulletTextColor)) {
            bulletTextColor = typedArray.getColor(R.styleable.AsociateBulletText_bulletTextColor,
                ContextCompat.getColor(context, R.color.text_secondary))
        }
    }

    private fun applyParams() {
        applyTextParams()
        applyBulletParams()
    }

    private fun applyTextParams() {
        text.text = bulletText
        bulletTextColor?.let {
            text.setTextColor(it)
        }
        bulletTextSize?.let {
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, it)
        }
    }

    private fun applyBulletParams() {
        setLayoutParams(text.textSize)
        bulletColor?.let { bullet.setBackgroundColor(it) }
    }

    private fun setLayoutParams(textSize: Float) {
        val bulletMarginTop = textSize.div(2).toInt()
        val bulletSize = bulletSize ?: context.resources.getDimensionPixelSize(R.dimen.bullet_size)

        bullet.layoutParams = LayoutParams(bulletSize, bulletSize).apply {
            setMargins(0, bulletMarginTop, 0, 0)
        }
    }
}
