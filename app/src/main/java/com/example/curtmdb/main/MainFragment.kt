package com.example.curtmdb.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.curtmdb.R
import com.example.curtmdb.architecture.InjectorUtil
import com.example.curtmdb.widget.PopularMovieAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PopularMovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        initView()

        viewModel.fetchData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, InjectorUtil.provideMainViewModelFactory()).get(MainViewModel::class.java)
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            adapter.data.addAll(it)
            adapter.notifyDataSetChanged()
        })
        viewModel.showSpinner.observe(viewLifecycleOwner, Observer { show ->
            spinner.visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    private fun initView() {
        with(list) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = VERTICAL
            }

            setHasFixedSize(true)
        }

        adapter = PopularMovieAdapter().also {
            list.adapter = it
        }
    }
}