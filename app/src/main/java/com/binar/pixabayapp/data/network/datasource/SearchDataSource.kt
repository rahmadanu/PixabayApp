package com.binar.pixabayapp.data.network.datasource

import com.binar.pixabayapp.data.network.service.PixabayApiService
import com.binar.pixabayapp.model.SearchResponse
import retrofit2.http.Query

interface SearchDataSource {
    suspend fun searchPhoto(query: String) : SearchResponse
}

class SearchDataSourceImpl(private val api: PixabayApiService): SearchDataSource {
    override suspend fun searchPhoto(query: String): SearchResponse {
        return api.searchPhoto(query)
    }

}

/* another api example
class InstagramApiDataSourceImpl(private val api: PixabayApiService //gotta be another api service) :
    SearchDataSource {

    override suspend fun searchPhoto(query: String): SearchResponse {
        return api.searchPhoto(query)
    }

}*/
