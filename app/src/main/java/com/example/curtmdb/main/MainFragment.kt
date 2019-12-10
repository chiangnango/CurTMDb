package com.example.curtmdb.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.curtmdb.MyApplication
import com.example.curtmdb.R
import com.example.curtmdb.util.MyLog
import com.example.curtmdb.util.MyViewModelFactory
import com.example.curtmdb.widget.PopularMovieAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MyViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PopularMovieAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter()
        initViewModel()
        initView()

        viewModel.fetchData()
    }

    private fun initAdapter() {
        adapter = PopularMovieAdapter().apply {
            favoriteClickListener = { _, movie ->
                viewModel.onFavoriteClicked(movie)
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.showSpinner.observe(viewLifecycleOwner, Observer { show ->
            spinner.visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        viewModel.popularMoviesResult.observe(viewLifecycleOwner, Observer {
            MyLog.d(TAG, "popularMoviesResult onChanged ${it.size}")
            showEmptyList(it.size == 0)
            adapter.submitList(it)
        })
    }

    private fun initView() {
        list.adapter = adapter
        list.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = VERTICAL
            }

            setHasFixedSize(true)
        }
    }

    private fun showEmptyList(show: Boolean) {
        // TODO: empty view
    }

    companion object {
        private val TAG = MainFragment::class.java.simpleName
    }
}