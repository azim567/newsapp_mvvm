package com.azimsiddiqui.newsapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azimsiddiqui.newsapp.data.model.Article
import com.azimsiddiqui.newsapp.data.model.NewsResponse
import com.azimsiddiqui.newsapp.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository):ViewModel() {

    private val _newsListLiveData=MutableLiveData<List<Article>>()
    val newsListLiveData:LiveData<List<Article>>
    get() = _newsListLiveData

    init {
        getAllNews()
    }

    private fun getAllNews() {
        viewModelScope.launch {
            val response=repository.getAllNews()
            if(response.isSuccessful){
                val result=response.body() as NewsResponse
                _newsListLiveData.value=result.articles as List<Article>
            }
            else{
                Log.i("newsError", "getAllNews: ${response.message()}")
            }
        }
    }


}
