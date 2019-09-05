package com.mycomics.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mycomics.R
import com.mycomics.data.model.Comic
import kotlinx.android.synthetic.main.dialog_comic_detail.*

class ComicDetailDialog : DialogFragment() {
    companion object {
        const val ARG_Comic = "arg_comic"
        fun instance(comic: Comic): ComicDetailDialog {
            val comicDetailDialog = ComicDetailDialog()
            val arguments = Bundle()
            arguments.putParcelable(ARG_Comic, comic)
            comicDetailDialog.arguments = arguments
            return comicDetailDialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_comic_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val comic = arguments?.getParcelable<Comic>(ARG_Comic)
        comic?.apply {
            tvComicDetail.text = this.desc
        }
        btnOk.setOnClickListener { dismiss() }

    }
}