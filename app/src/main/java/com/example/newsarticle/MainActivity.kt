package com.example.newsarticle

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsarticle.model.Articles

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NewsAdapter
    private var articles= listOf<Articles>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_View)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //initialize adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(this, mutableListOf())
        recyclerView.adapter= adapter

        progressBar.visibility = View.VISIBLE

        mainViewModel.getArticles().observe(this){
            //for sorting by date when user pressed
            articles = it

            progressBar.visibility = View.GONE
            //giving data to adapter
            adapter.setData(articles)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_first -> sortList(true)
            R.id.old_first -> sortList(false)
        }
        return true
    }

    private fun sortList(descending: Boolean) {
        if(articles.isNotEmpty()) {
            val sortedArticles = if (descending) {
                articles.sortedByDescending { it.publishedAt }
            } else articles.sortedBy { it.publishedAt }
            adapter.setData(sortedArticles)
        }
    }
}