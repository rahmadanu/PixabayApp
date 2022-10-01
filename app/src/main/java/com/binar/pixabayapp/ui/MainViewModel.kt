package com.binar.pixabayapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.pixabayapp.data.repository.SearchRepository
import com.binar.pixabayapp.model.SearchResponse
import com.binar.pixabayapp.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainViewModel(private val repository: SearchRepository) : ViewModel() {

    private var _searchResult =  MutableLiveData<Resource<SearchResponse>>()
    val searchResult: LiveData<Resource<SearchResponse>> get() = _searchResult

    fun searchPost(query: String) {
        _searchResult.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.searchPhoto(query)
            viewModelScope.launch(Dispatchers.Main) {
                _searchResult.postValue(data)
            }
        }
    }
}