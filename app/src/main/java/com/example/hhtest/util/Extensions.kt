package com.example.hhtest.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast

fun Context.dpToPx(dp: Int) = dp * resources.displayMetrics.density

fun Context.spToPx(ps: Int) = ps * resources.displayMetrics.scaledDensity

fun EditText.moveToNextEditTextAfterClickEnter(nextET: EditText) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
            nextET.requestFocus()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}

fun Context.showToastMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}