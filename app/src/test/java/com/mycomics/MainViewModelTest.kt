package com.mycomics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mycomics.data.model.Comic
import com.mycomics.data.repository.ComicRepositoryImpl
import com.mycomics.ui.activity.MainActivity
import com.mycomics.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.net.UnknownHostException


@RunWith(BlockJUnit4ClassRunner::class)
class MainViewModelTest {


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var comicRepository: ComicRepositoryImpl
    lateinit var mainViewModel: MainViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(comicRepository)
    }

    @Test
    fun fetchComics_successWithOrderedAndDistinct() {
        val comics = listOf(
            Comic("Demo 1", "Demo 1", "Demo 1"), Comic("Demo 1", "Demo 1", "Demo 1"),
            Comic("Demo", "Demo", "Demo")
        )

        //Here in expected output we have removed duplicate entry and based on title sorting
        //the last entry should come first

        val expectedComics = listOf(Comic("Demo", "Demo", "Demo"), Comic("Demo 1", "Demo 1", "Demo 1"))
        every {comicRepository.fetchComics()} returns (Single.just(comics))

        mainViewModel.fetchComics()

        Assert.assertEquals(expectedComics, mainViewModel.comics.value)
        Assert.assertEquals(MainViewModel.LoadingState.SUCCESS, mainViewModel.loadingState.value)
        Assert.assertEquals(null, mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchComics_successEmptyComicList() {
        val comics = listOf<Comic>()
        every {comicRepository.fetchComics()} returns (Single.just(comics))

        mainViewModel.fetchComics()

        Assert.assertEquals(null, mainViewModel.comics.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("No Comic found", mainViewModel.errorMessage.value)
    }


    @Test
    fun fetchComics_networkError() {

        every {comicRepository.fetchComics()} returns (Single.error(UnknownHostException("Abc")))

        mainViewModel.fetchComics()

        Assert.assertEquals(null, mainViewModel.comics.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("No Network", mainViewModel.errorMessage.value)
    }


    @Test
    fun fetchComics_otherError() {
        every{comicRepository.fetchComics()} returns Single.error(RuntimeException("Abc"))

        mainViewModel.fetchComics()

        Assert.assertEquals(null, mainViewModel.comics.value)
        Assert.assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        Assert.assertEquals("Abc", mainViewModel.errorMessage.value)
    }

    @Test
    fun getActivityTest(){
        val activityClass = mainViewModel.getActivity()
        assertTrue(activityClass == MainActivity::class.java)
    }
}