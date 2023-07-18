package br.com.chalenge.rickmorty.data.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Retrofit {

    fun provideRetrofit(baseUrl: String, moshi: Moshi, client: OkHttpClient): Retrofit {
       return Retrofit.Builder()
           .baseUrl(baseUrl)
           .client(client)
           .addConverterFactory(MoshiConverterFactory.create(moshi))
           .build()
    }
}