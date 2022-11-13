package com.example.newsarticle.utils

import android.util.Log
import com.example.newsarticle.model.Articles
import com.example.newsarticle.model.NewsApiResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpClient {

    fun getData():List<Articles>{
        try{
            val path = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"
            val url = URL(path)
            val httpUrlConnection = url.openConnection() as HttpURLConnection
            httpUrlConnection.requestMethod = "GET"
            httpUrlConnection.connect()
            if (httpUrlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream =
                    BufferedReader(InputStreamReader(httpUrlConnection.inputStream))
                var inputLine: String?
                val response = StringBuffer()
                while (inputStream.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                inputStream.close()
                if (response.isNotEmpty()) {
                    val apiParser = Gson().fromJson(response.toString(), NewsApiResponse::class.java)
                    if (apiParser != null) {
                        return apiParser.articles
                    }
                } else
                    Log.d("News", "response is empty")
            }
        } catch (e:Exception){
            e.printStackTrace()
        }
        return emptyList()
    }
}