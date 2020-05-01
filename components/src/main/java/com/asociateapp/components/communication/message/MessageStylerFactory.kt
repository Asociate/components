package com.asociateapp.components.communication.message

internal object MessageStylerFactory {

    fun create(type: AsociateMessageTypes) = when(type) {
        AsociateMessageTypes.ELEVATED -> ElevatedMessageStyler()
        AsociateMessageTypes.FLAT -> FlatMessageStyler()
    }
}