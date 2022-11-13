package com.example.newsarticle

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsarticle.model.Articles
import java.io.FileNotFoundException

class NewsAdapter(private val context: Context, private val dataList: MutableList<Articles>):RecyclerView.Adapter<NewsAdapter.ArticlesViewHolder>() {
    inner class ArticlesViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val newsImage: ImageView = itemView.findViewById(R.id.news_image)
        val newsTitle: TextView = itemView.findViewById(R.id.new_title)
        val newsDesc: TextView = itemView.findViewById(R.id.news_desc)
        val newsSource: TextView = itemView.findViewById(R.id.news_source)
        val newsTime: TextView = itemView.findViewById(R.id.news_time)
        var url:String = ""

        init {
            itemView.setOnClickListener {
                if (url.isNotEmpty()) {
                    //to open the browser
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_card,parent,false)
        return ArticlesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = dataList[position]

        holder.newsTitle.text = article.title
        holder.newsDesc.text = article.description
        holder.url = article.url.toString()
        val source = "Source: ${article.source?.name}"
        holder.newsSource.text  = source
        holder.newsTime.text = article.publishedAt?.replace("T"," ")?.replace("Z","")

        try {
            Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        } catch (e:FileNotFoundException){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(memes:List<Articles>){
        this.dataList.clear()
        this.dataList.addAll(memes)
        notifyDataSetChanged()
    }
}