package com.mycomics.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mycomics.R
import com.mycomics.data.model.Comic
import com.mycomics.data.remote.WebServices
import com.mycomics.data.repository.ComicRepositoryImpl
import com.mycomics.ui.adapter.ComicAdapter
import com.mycomics.ui.adapter.listener.ComicClickListener
import com.mycomics.ui.dialog.ComicDetailDialog
import com.mycomics.viewmodel.MainViewModel
import com.mycomics.viewmodel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var comicAdapter: ComicAdapter
    private val comicClickListener: ComicClickListener = object : ComicClickListener {
        override fun onClick(comic: Comic) {
            val comicDetailDialog = ComicDetailDialog.instance(comic)
            comicDetailDialog.show(supportFragmentManager,"COMIC_DETAIL")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProviders.of(this, MainViewModelFactory(ComicRepositoryImpl(WebServices.instance)))
            .get(MainViewModel::class.java)
        viewModel.comics.observe(this, Observer {
            comicAdapter.comics.clear()
            comicAdapter.comics.addAll(it)
            comicAdapter.notifyDataSetChanged()

        })

        viewModel.errorMessage.observe(this, Observer {
            tvMessage.text = it
        })

        viewModel.loadingState.observe(this, Observer {
            when (it) {
                MainViewModel.LoadingState.LOADING -> displayProgressbar()
                MainViewModel.LoadingState.SUCCESS -> displayList()
                MainViewModel.LoadingState.ERROR -> displayMessageContainer()
                else -> displayMessageContainer()
            }
        })
        if (viewModel.lastFetchedTime == null) {
            viewModel.fetchComics()
        }

        btnRetry.setOnClickListener {
            viewModel.fetchComics()
        }

    }

    private fun displayProgressbar() {
        progressbar.visibility = View.VISIBLE
        rvComics.visibility = View.GONE
        llMessageContainer.visibility = View.GONE
    }

    private fun displayMessageContainer() {
        llMessageContainer.visibility = View.VISIBLE
        rvComics.visibility = View.GONE
        progressbar.visibility = View.GONE
    }

    private fun displayList() {

        llMessageContainer.visibility = View.GONE
        rvComics.visibility = View.VISIBLE
        progressbar.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        rvComics.layoutManager = LinearLayoutManager(this)
        comicAdapter = ComicAdapter(mutableListOf(), comicClickListener)
        rvComics.adapter = comicAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.fetchComics()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }

    }

}
