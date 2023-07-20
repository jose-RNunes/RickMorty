package br.com.chalenge.rickmorty.di

import br.com.chalenge.rickmorty.data.remote.ErrorInterceptor
import br.com.chalenge.rickmorty.data.remote.MoshiFactory
import br.com.chalenge.rickmorty.data.remote.OkHttpClientFactory
import br.com.chalenge.rickmorty.data.remote.RetrofitFactory
import br.com.chalenge.rickmorty.data.remote.RickMortyApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRickMortyApi(retrofit: Retrofit): RickMortyApi {
        return retrofit.create(RickMortyApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory.provideRetrofit(
            baseUrl = "https://rickandmortyapi.com/api/",
            moshi = moshi,
            client = okHttpClient
        )
    }

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return MoshiFactory.build()
    }

    @Provides
    fun providesHttpClient(errorInterceptor: ErrorInterceptor): OkHttpClient {
        return OkHttpClientFactory.provideOkHttpClient(errorInterceptor)
    }
}