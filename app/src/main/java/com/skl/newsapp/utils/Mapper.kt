package com.skl.newsapp.utils

interface Mapper <NetworkEntity, DomainModel> {

    fun mapFromNetwork(networkEntity: NetworkEntity): DomainModel

    fun mapToNetwork(domainModel: DomainModel): NetworkEntity

}