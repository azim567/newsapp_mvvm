package com.azimsiddiqui.newsapp.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azimsiddiqui.newsapp.R
import com.azimsiddiqui.newsapp.data.model.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_row_news.view.*

class NewsRecyclerAdapter(private var listener: NewsItemClickListener) : RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    private  var newList=ArrayList<Article>()

    inner class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(news: Article) {
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.news_thumbnail)

            with(itemView){
                tv_news_title.text = news.title
                tv_news_desc.text=news.description
                tv_author.text=news.author
                tv_published_on.text= news.publishedAt.split("T")[0]
                parent_layout.setOnClickListener {
                    listener.onItemClick(news.url)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.item_row_news,parent,false)
       return NewsViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newList[position])
    }

    override fun getItemCount(): Int {
       return newList.size
    }

    fun setData(list:List<Article>){
        newList.clear()
        newList.addAll(list)
        notifyDataSetChanged()
    }
}