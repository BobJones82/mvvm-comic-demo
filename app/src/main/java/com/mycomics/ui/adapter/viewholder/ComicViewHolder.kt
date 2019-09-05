package com.mycomics.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mycomics.data.model.Comic
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_comic.view.*

class ComicViewHolder(item: View) : RecyclerView.ViewHolder(item) {
    fun bindItem(comic: Comic) {
        itemView.tvComicTitle.text = comic.title
        Picasso.get().load(comic.image).into(itemView.ivComicImage)
    }
}