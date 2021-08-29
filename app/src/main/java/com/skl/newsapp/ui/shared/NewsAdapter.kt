package com.skl.newsapp.ui.shared

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skl.newsapp.R
import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.databinding.NewsItemDisplayBinding

class NewsAdapter(private val listener: OnItemClickListener) :
    ListAdapter<DomainArticle, NewsAdapter.NewsViewHolder>(NewsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            NewsItemDisplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface OnItemClickListener {
        fun OnItemClick(domainArticle: DomainArticle)
    }

    inner class NewsViewHolder(private val binding: NewsItemDisplayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val TAG = "Viewholder"

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val roomArticle = getItem(position)
                    listener.OnItemClick(roomArticle)
                }
            }
        }

        fun bind(domainArticle: DomainArticle) {
            binding.apply {
                title.text = domainArticle.title
                description.text = domainArticle.description
                if (domainArticle.urlToImage != null) {
                    val uri = Uri.parse(domainArticle.urlToImage).buildUpon().scheme("https").build().toString()
                    Glide.with(itemView)
                        .load(uri)
                        .placeholder(R.drawable.ic_placeholder)
                        .override(100)
                        .error(R.drawable.ic_broken_image)
                        .thumbnail(0.35f)
                        .into(image)
                }else{
                    Glide.with(itemView).load(R.drawable.ic_image_not_found).into(image)
                }
                sourceName.text = domainArticle.source.name
            }
        }
    }

}