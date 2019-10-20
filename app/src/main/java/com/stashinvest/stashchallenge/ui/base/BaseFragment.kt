package com.stashinvest.stashchallenge.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import javax.inject.Inject

abstract class BaseFragment<P : BasePresenter<V>, V : BaseView> : Fragment() {

    @Inject
    protected lateinit var presenter: P

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this as V, lifecycle)
    }
}

