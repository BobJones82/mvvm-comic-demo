package com.mycomics.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycomics.R
import com.mycomics.data.model.Comic
import com.mycomics.ui.adapter.listener.ComicClickListener
import com.mycomics.ui.adapter.viewholder.ComicViewHolder

class ComicAdapter constructor(val comics: MutableList<Comic>, private val comicClickListener: ComicClickListener) :
    RecyclerView.Adapter<ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ComicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false))

    override fun getItemCount() = comics.size

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bindItem(comics[position])
        holder.itemView.setOnClickListener {
            comicClickListener.onClick(comics[holder.adapterPosition])
        }
    }
}
