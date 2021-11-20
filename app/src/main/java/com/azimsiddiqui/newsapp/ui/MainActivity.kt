package com.azimsiddiqui.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azimsiddiqui.newsapp.R
import com.azimsiddiqui.newsapp.data.model.Article
import com.azimsiddiqui.newsapp.databinding.ActivityMainBinding
import com.azimsiddiqui.newsapp.ui.viewmodel.NewsViewModel
import com.azimsiddiqui.newsapp.util.Constants.EXTRA_NEWS_URL
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() , NewsItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsRecycleAdapter: NewsRecyclerAdapter
    private var newsList = ArrayList<Article>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLiveEvent()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvNewsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            hasFixedSize()
        }
    }

    private fun observeLiveEvent() {
        binding.pbLoading.visibility = View.VISIBLE
        setVisibility(binding.labelTopHeadlines,false)
        viewModel.newsListLiveData.observe(this, { userResponse ->
            userResponse?.let {
                newsList.addAll(it)
                newsRecycleAdapter = NewsRecyclerAdapter(this)
                binding.rvNewsList.adapter = newsRecycleAdapter
                if (newsList.isEmpty()) {
                    setVisibility(binding.labelTopHeadlines,false)
                    setVisibility(binding.tvNoResult,true)
                } else {
                    setVisibility(binding.labelTopHeadlines,true)
                    setVisibility(binding.tvNoResult,false)
                }
                binding.pbLoading.visibility = View.GONE
                newsRecycleAdapter.setData(newsList)
            }
        })

    }

    private fun setVisibility(view: View, isVisible: Boolean) {
        view.visibility= if(isVisible) View.VISIBLE else View.GONE
    }

    override fun onItemClick(url: String) {
        val intent= Intent(this,NewsDetailActivity::class.java)
        intent.putExtra(EXTRA_NEWS_URL,url)
        startActivity(intent)
    }

}