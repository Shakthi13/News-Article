package com.example.newsarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsarticle.model.Articles
import com.example.newsarticle.utils.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    fun getArticles():LiveData<List<Articles>>{
        val articles = MutableLiveData<List<Articles>>()
        CoroutineScope(Dispatchers.IO).launch{
            val response = HttpClient.getData()
            articles.postValue(response)
        }
        return articles
    }
}