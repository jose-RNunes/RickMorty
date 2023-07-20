package br.com.chalenge.rickmorty.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpClientFactory {

    fun provideOkHttpClient(errorInterceptor: ErrorInterceptor) = OkHttpClient.Builder()
        .addInterceptor(errorInterceptor)
        .addInterceptor(loggingInterceptor())
        .build()

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}