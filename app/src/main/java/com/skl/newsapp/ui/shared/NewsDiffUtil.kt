package com.skl.newsapp.ui.shared

import androidx.recyclerview.widget.DiffUtil
import com.skl.newsapp.data.repository.local.model.DomainArticle

class NewsDiffUtil: DiffUtil.ItemCallback<DomainArticle>() {
    override fun areItemsTheSame(oldItem: DomainArticle, newItem: DomainArticle): Boolean =
        oldItem.title == newItem.title


    override fun areContentsTheSame(oldItem: DomainArticle, newItem: DomainArticle): Boolean =
        oldItem == newItem

}