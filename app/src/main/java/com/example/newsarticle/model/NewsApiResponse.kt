package com.example.newsarticle.model

data class NewsApiResponse (
    var status   : String?             = null,
    var articles : ArrayList<Articles> = arrayListOf()
)
