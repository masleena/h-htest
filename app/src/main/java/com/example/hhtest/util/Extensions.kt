package com.example.hhtest.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun Context.dpToPx(dp: Int) = dp * resources.displayMetrics.density

fun Context.psToPx(ps: Int) = ps * resources.displayMetrics.scaledDensity

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun EditText.moveToNextEditTextAfterClickEnter(nextET: EditText) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
            nextET.requestFocus()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}