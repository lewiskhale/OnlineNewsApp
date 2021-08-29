package com.skl.newsapp.data.repository.network.model

import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.utils.Mapper

class NetworkArticleMapper: Mapper<NetworkArticle, DomainArticle> {

    override fun mapFromNetwork(networkEntity: NetworkArticle): DomainArticle {
        return DomainArticle(
            author = networkEntity.author,
            content = networkEntity.content,
            description = networkEntity.description,
            publishedAt = networkEntity.publishedAt,
            source = networkEntity.source,
            title = networkEntity.title,
            url = networkEntity.url,
            urlToImage = networkEntity.urlToImage,
            saved = false
        )
    }

    override fun mapToNetwork(domainModel: DomainArticle): NetworkArticle {
        return NetworkArticle(
            author = domainModel.author,
            content = domainModel.content,
            description = domainModel.description,
            publishedAt = domainModel.publishedAt,
            source = domainModel.source,
            title = domainModel.title,
            url = domainModel.url,
            urlToImage = domainModel.urlToImage
        )
    }

    fun fromNetworkList(networkArticleList: List<NetworkArticle>):List<DomainArticle>
    {
        return networkArticleList.map { article -> mapFromNetwork(article) }
    }

    fun toNetworkList(networkArticleList: List<NetworkArticle>):List<DomainArticle>
    {
        return networkArticleList.map { article -> mapFromNetwork(article) }
    }
}