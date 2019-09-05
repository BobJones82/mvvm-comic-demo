package com.mycomics.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycomics.data.model.Comic
import com.mycomics.data.repository.ComicRepository
import com.mycomics.ui.activity.MainActivity
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException
import java.util.*

class MainViewModel constructor(private val comicRepository: ComicRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    fun fetchComics() {
        loadingState.value = LoadingState.LOADING
        disposable.add(
            comicRepository.fetchComics()
                .map {
                    comics-> comics.distinct().sortedBy { comic -> comic.title }
                }
                .subscribe({
                    lastFetchedTime = Date()
                    if (it.isEmpty()) {
                        errorMessage.value = "No Comic found"
                        loadingState.value = LoadingState.ERROR
                    } else {
                        comics.value = it
                        loadingState.value = LoadingState.SUCCESS
                    }
                }, {
                    lastFetchedTime = Date()

                    it.printStackTrace()
                    when (it) {
                        is UnknownHostException -> errorMessage.value = "No Network"
                        else -> errorMessage.value = it.localizedMessage
                    }

                    loadingState.value = LoadingState.ERROR
                })
        )

    }

    var lastFetchedTime: Date? = null

    val comics: MutableLiveData<List<Comic>> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val loadingState = MutableLiveData<LoadingState>()

    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getActivity(): Class<out Activity>{
        return MainActivity::class.java
    }
}