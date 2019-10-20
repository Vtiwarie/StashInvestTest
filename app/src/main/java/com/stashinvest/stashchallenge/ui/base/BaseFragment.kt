package com.stashinvest.stashchallenge.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import javax.inject.Inject

abstract class BaseFragment<P : BasePresenter<T>, T : BaseView> : Fragment() {

    abstract val vmClass: Class<P>
    protected lateinit var presenter: P

    @Inject
    fun injectPresenter(presenter: P) {
        this.presenter = presenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(view as T, lifecycle)
    }
}

