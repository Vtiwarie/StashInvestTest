package com.stashinvest.stashchallenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.GridLayoutManager
import com.stashinvest.stashchallenge.App
import com.stashinvest.stashchallenge.R
import com.stashinvest.stashchallenge.ui.base.BaseFragment
import com.stashinvest.stashchallenge.ui.presenter.MainPresenter
import com.stashinvest.stashchallenge.ui.views.MainView
import com.stashinvest.stashchallenge.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainPresenter, MainView>(), MainView {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override val vmClass = MainPresenter::class.java
    private val space: Int by lazy { requireContext().resources.getDimensionPixelSize(R.dimen.image_space) }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchPhrase.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.search(v.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = presenter.adapter
        recyclerView.addItemDecoration(SpaceItemDecoration(space, space, space, space))
    }

    override fun showProgess(visible: Boolean) {
        progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showError(t: Throwable) {
        progressBar.visibility = GONE
        Toast.makeText(activity!!, t.message, Toast.LENGTH_LONG).show()
    }

    fun onImageLongPress(id: String, uri: String?) {
        //todo - implement new feature
    }

}
