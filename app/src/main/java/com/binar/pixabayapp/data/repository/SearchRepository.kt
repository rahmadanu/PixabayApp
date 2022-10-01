package com.binar.pixabayapp.data.repository

import com.binar.pixabayapp.data.network.datasource.SearchDataSource
import com.binar.pixabayapp.model.SearchResponse
import com.binar.pixabayapp.wrapper.Resource

interface SearchRepository {
    suspend fun searchPhoto(query: String) : Resource<SearchResponse>
}

class SearchRepositoryImpl(private val dataSource: SearchDataSource): SearchRepository {
    override suspend fun searchPhoto(query: String): Resource<SearchResponse> {
        return try {
            val data = dataSource.searchPhoto(query)
            if (data.posts.isNullOrEmpty()) Resource.Empty() else Resource.Success(data)
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}