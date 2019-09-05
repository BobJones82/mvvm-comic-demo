package com.mycomics.data.repository

import com.mycomics.data.model.Comic
import com.mycomics.data.remote.WebServices
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class ComicRepositoryImpl(private val webServices: WebServices) : ComicRepository {
    override fun fetchComics(): Single<List<Comic>> {
        return webServices.fetchComics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    }
}