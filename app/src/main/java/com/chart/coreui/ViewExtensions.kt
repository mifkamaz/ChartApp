package com.chart.coreui

import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText

fun Button.bindAsActionDoneFor(editText: EditText) {
    setOnClickListener {
        editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }
}

fun EditText.setOnEditorActionDoneListener(listener: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            listener()
        }
        return@setOnEditorActionListener false
    }
}