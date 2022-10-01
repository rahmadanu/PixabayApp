package com.binar.pixabayapp.provider

import android.content.Context
import com.binar.pixabayapp.data.network.datasource.SearchDataSource
import com.binar.pixabayapp.data.network.datasource.SearchDataSourceImpl
import com.binar.pixabayapp.data.network.service.PixabayApiService
import com.binar.pixabayapp.data.repository.SearchRepository
import com.binar.pixabayapp.data.repository.SearchRepositoryImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor

object ServiceLocator {

    fun provideChucker(appContext: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(appContext).build()
    }

    fun provideApiService(chuckerInterceptor: ChuckerInterceptor): PixabayApiService {
        return PixabayApiService.invoke(chuckerInterceptor)
    }

    fun provideDataSource(apiService: PixabayApiService): SearchDataSource {
        return SearchDataSourceImpl(apiService)
    }

    fun provideRepository(context: Context): SearchRepository {
        return SearchRepositoryImpl(provideDataSource(provideApiService(provideChucker(context))))
    }
}