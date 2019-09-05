package com.mycomics.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycomics.data.repository.ComicRepository
import com.mycomics.viewmodel.MainViewModel

class MainViewModelFactory constructor(private val comicRepository: ComicRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(comicRepository) as T
    }

}