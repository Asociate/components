package com.asociateapp.components.communication.message

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.asociateapp.components.R
import com.asociateapp.components.common.RowManager

class AsociateMessage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val rowBuilder = RowManager(TAG)

    init {
        initializeParams(attrs)
        addRow()
        buildMessage()
    }

    private var type = AsociateMessageTypes.ELEVATED
    private var message = ""
    private var icon: Drawable? = null

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

            typedArray.recycle()
        }
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
            MessageIcon(messageContainer.context).apply {
                setImage(icon)
                messageContainer.addView(this)
            }
        }
    }

    private fun setMessageText(messageContainer: LinearLayout) {
        TextView(messageContainer.context).apply {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            text = message
            messageContainer.addView(this)
        }
    }

    companion object {
        internal const val TAG = "AsociateMessage"
    }

}