package com.stashinvest.stashchallenge.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stashinvest.stashchallenge.App
import com.stashinvest.stashchallenge.R
import com.stashinvest.stashchallenge.api.model.ImageResult
import com.stashinvest.stashchallenge.ui.adapter.ViewModelAdapter
import com.stashinvest.stashchallenge.ui.factory.ImageFactory
import com.stashinvest.stashchallenge.util.SpaceItemDecoration
import javax.inject.Inject
import kotlin.math.min

/**
 * @author Vishaan Tiwarie
 *
 * Create a dialog to display selected image metadata, image,
 * and similar images
 */
class PopUpDialogFragment : DialogFragment() {

    @Inject
    lateinit var vmAdapter: ViewModelAdapter

    @Inject
    lateinit var imageFactory: ImageFactory

    private val space: Int by lazy { requireContext().resources.getDimensionPixelSize(R.dimen.image_space) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)

        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog_Alert)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.layout_dialog_main, null)

        with(arguments) {
            val title = this?.getString(ARG_TITLE)
            val artist = this?.getString(ARG_ARTIST)
            val caption = this?.getString(ARG_CAPTION)
            val uri = this?.getString(ARG_URI)

            val similarImagesArg = this?.getParcelableArrayList<ImageResult>(ARG_SIMILAR_IMAGES)
            val similarImages = similarImagesArg?.subList(0, min(3, similarImagesArg.size))?.toList()
                    ?: emptyList()

            view?.findViewById<TextView>(R.id.title)?.text = "${getString(R.string.mf_title)} $title"
            view?.findViewById<TextView>(R.id.artist)?.text = "${getString(R.string.mf_artist)} $artist"
            view?.findViewById<TextView>(R.id.caption)?.text = "${getString(R.string.mf_caption)} $caption"

            val imageView = view?.findViewById<ImageView>(R.id.image)
            imageView?.let {
                Glide.with(context!!)
                        .load(Uri.parse(uri))
                        .into(imageView)
            }

            val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView?.apply {
                this.layoutManager = GridLayoutManager(context, 3)
                this.adapter = vmAdapter
                this.addItemDecoration(SpaceItemDecoration(space, space, space, space))
            }

            val label_similar_images = view?.findViewById<TextView>(R.id.label_similar_images)

            if (similarImages.isEmpty()) {
                label_similar_images?.visibility = View.GONE
                recyclerView?.visibility = View.GONE
            } else {
                label_similar_images?.visibility = View.VISIBLE
                recyclerView?.visibility = View.VISIBLE
                updateImages(similarImages)
            }
        }

        return AlertDialog.Builder(context)
                .setView(view)
                .create()
    }

    fun updateImages(images: List<ImageResult>) {
        val viewModels = images.map { imageFactory.createImageViewModel(it, null) }
        vmAdapter.setViewModels(viewModels)
    }

    companion object {
        const val ARG_TITLE = "ARG_TITLE"
        const val ARG_ARTIST = "ARG_ARTIST"
        const val ARG_CAPTION = "ARG_CAPTION"
        const val ARG_URI = "ARG_URI"
        const val ARG_SIMILAR_IMAGES = "ARG_SIMILAR_IMAGES"

        fun newInstance(title: String,
                        artist: String,
                        caption: String,
                        uri: String?,
                        similarImages: ArrayList<ImageResult>) = PopUpDialogFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_ARTIST, artist)
                putString(ARG_CAPTION, caption)
                putString(ARG_URI, uri)
                putParcelableArrayList(ARG_SIMILAR_IMAGES, similarImages)
            }
        }
    }
}
