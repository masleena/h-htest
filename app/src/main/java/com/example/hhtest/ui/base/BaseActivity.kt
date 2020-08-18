package com.example.hhtest.ui.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    val uiDisposables = CompositeDisposable()

    fun showMessage(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }

    fun hideKeyboard() {
        val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null) {
            keyboard.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        uiDisposables.dispose()
    }

    protected fun Disposable.addToDisposables() {
        uiDisposables.add(this)
    }

}