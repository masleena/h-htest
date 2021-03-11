package com.example.hhtest.ui.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment(resId: Int) : Fragment(resId) {

    val uiDisposables = CompositeDisposable()

    fun hideKeyboard() = (requireActivity() as BaseActivity).hideKeyboard()

    override fun onDestroy() {
        super.onDestroy()
        uiDisposables.dispose()
    }

    protected fun Disposable.addToDisposables() {
        uiDisposables.add(this)
    }
}