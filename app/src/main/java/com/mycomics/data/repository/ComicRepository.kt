package com.mycomics.data.repository

import com.mycomics.data.model.Comic
import io.reactivex.Single

interface ComicRepository {
    fun fetchComics(): Single<List<Comic>>
}