package com.example.hhtest.presenter

import com.example.hhtest.ui.base.IBaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T: IBaseView> {

    protected lateinit var _view: T

    private val disposables = CompositeDisposable()

    fun onCreateView(view: T) {
        _view = view
    }

    fun onViewDestroy() {
        disposables.dispose()
    }

    protected fun Disposable.addToDisposables() {
        disposables.add(this)
    }
}