package com.asociateapp.components.communication.message

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.asociateapp.components.R
import com.asociateapp.components.common.RowManager

class AsociateMessage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var type = AsociateMessageTypes.ELEVATED
    private var message = ""
    private var icon: Drawable? = null
    private var messageColor = ContextCompat.getColor(context, R.color.text_primary)
    private var messageSize = resources.getDimensionPixelSize(R.dimen.message_text_size).toFloat()

    private val rowBuilder = RowManager(TAG)

    init {
        initializeParams(attrs)
        applyMessageStyle()
        addRow()
        buildMessage()
    }

    private fun initializeParams(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AsociateMessage)

            if (typedArray.hasValue(R.styleable.AsociateMessage_messageType)) {
                type = AsociateMessageTypes.values()[typedArray.getInt(R.styleable.AsociateMessage_messageType, 0)]
            }

            if (typedArray.hasValue(R.styleable.AsociateMessage_message)) {
                message = typedArray.getString(R.styleable.AsociateMessage_message) ?: ""
            }

            if (typedArray.hasValue(R.styleable.AsociateMessage_icon)) {
                icon = typedArray.getDrawable(R.styleable.AsociateMessage_icon)
            }

            if (typedArray.hasValue(R.styleable.AsociateBulletText_bulletTextSize)) {
                messageSize = typedArray.getDimension(R.styleable.AsociateMessage_messageSize,
                    resources.getDimensionPixelSize(R.dimen.message_text_size).toFloat())
            }
            if (typedArray.hasValue(R.styleable.AsociateBulletText_bulletTextColor)) {
                messageColor = typedArray.getColor(R.styleable.AsociateMessage_messageColor,
                    ContextCompat.getColor(context, R.color.text_primary))
            }

            typedArray.recycle()
        }
    }

    private fun applyMessageStyle() {
        val styler = MessageStylerFactory.create(type)
        styler.apply(this)
    }

    private fun addRow() {
        rowBuilder.buildOn(this)
    }

    private fun buildMessage() {
        val messageContainer = rowBuilder.findOn(this, 0)
        setMessageIcon(messageContainer)
        setMessageText(messageContainer)
    }

    private fun setMessageIcon(messageContainer: LinearLayout) {
        icon?.let { icon ->
            MessageIcon(context).apply {
                setImage(icon)

                val horizontalMargin = resources.getDimensionPixelSize(R.dimen.message_icon_horizontal_margin)
                val verticalMargin = resources.getDimensionPixelSize(R.dimen.message_icon_vertical_margin)
                (layoutParams as LinearLayout.LayoutParams).setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)

                messageContainer.addView(this)
            }
        }
    }

    private fun setMessageText(messageContainer: LinearLayout) {
        TextView(context).apply {
            // set position and size attributes
            val marginEnd = resources.getDimensionPixelSize(R.dimen.message_icon_horizontal_margin)
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT).also { it.setMargins(0, 0, marginEnd, 0) }

            // set text attributes
            text = message
            setTextSize(TypedValue.COMPLEX_UNIT_PX, messageSize)
            setTextColor(messageColor)

            messageContainer.addView(this)
        }
    }

    companion object {
        internal const val TAG = "AsociateMessage"
    }

}