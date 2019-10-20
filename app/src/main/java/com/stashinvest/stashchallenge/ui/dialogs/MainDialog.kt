package com.stashinvest.stashchallenge.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.stashinvest.stashchallenge.R

class MainDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog_Alert)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.layout_dialog_main, null)

        with(arguments) {
            val title = this?.getString(ARG_TITLE)
            val artist = this?.getString(ARG_ARTIST)
            val caption = this?.getString(ARG_CAPTION)
            val uri = this?.getString(ARG_URI)

            view?.findViewById<TextView>(R.id.title)?.text = "${getString(R.string.mf_title)} $title"
            view?.findViewById<TextView>(R.id.artist)?.text = "${getString(R.string.mf_artist)} $artist"
            view?.findViewById<TextView>(R.id.caption)?.text = "${getString(R.string.mf_caption)} $caption"

            val imageView = view?.findViewById<ImageView>(R.id.image)
            imageView?.let {
                Glide.with(context!!)
                        .load(Uri.parse(uri))
                        .into(imageView)
            }
        }

        return AlertDialog.Builder(context)
                .setView(view)
                .create()
    }

    companion object {
        const val ARG_TITLE = "ARG_TITLE"
        const val ARG_ARTIST = "ARG_ARTIST"
        const val ARG_CAPTION = "ARG_CAPTION"
        const val ARG_URI = "ARG_URI"

        fun newInstance(title: String,
                        artist: String,
                        caption: String,
                        uri: String?) = MainDialog().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_ARTIST, artist)
                putString(ARG_CAPTION, caption)
                putString(ARG_URI, uri)
            }
        }
    }
}