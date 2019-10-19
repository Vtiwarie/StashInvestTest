package com.stashinvest.stashchallenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.stashinvest.stashchallenge.App
import com.stashinvest.stashchallenge.R
import com.stashinvest.stashchallenge.api.StashImageService
import com.stashinvest.stashchallenge.api.model.ImageResult
import com.stashinvest.stashchallenge.ui.adapter.ViewModelAdapter
import com.stashinvest.stashchallenge.ui.factory.ImageFactory
import com.stashinvest.stashchallenge.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    @Inject
    lateinit var adapter: ViewModelAdapter
    @Inject
    lateinit var stashImageService: StashImageService
    @Inject
    lateinit var imageFactory: ImageFactory

    private val space: Int by lazy { requireContext().resources.getDimensionPixelSize(R.dimen.image_space) }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchPhrase.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
                return@setOnEditorActionListener true
            }
            false
        }

        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpaceItemDecoration(space, space, space, space))
    }

    private fun search() {
        progressBar.visibility = View.VISIBLE
        stashImageService.searchImages(searchPhrase.text.toString())
                .doOnError { error ->
                    this.showError(error)
                }
                .subscribe { response ->
                    progressBar.visibility = GONE

                    val images = response.images
                    updateImages(images)
                }
    }

    private fun updateImages(images: List<ImageResult>) {
        val viewModels = images.map { imageFactory.createImageViewModel(it, ::onImageLongPress) }
        adapter.setViewModels(viewModels)
    }

    private fun showError(t: Throwable) {
        progressBar.visibility = GONE
        Toast.makeText(activity!!, t.message, Toast.LENGTH_LONG).show()
    }

    fun onImageLongPress(id: String, uri: String?) {
        //todo - implement new feature
    }
}
