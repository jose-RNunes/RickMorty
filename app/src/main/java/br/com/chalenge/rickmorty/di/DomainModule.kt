package br.com.chalenge.rickmorty.di

import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToModelMapperImpl
import br.com.chalenge.rickmorty.data.mapper.CharacterResultResponseToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResultResponseToModelMapperImpl
import br.com.chalenge.rickmorty.data.remote.MoshiFactory
import br.com.chalenge.rickmorty.data.remote.OkHttpClientFactory
import br.com.chalenge.rickmorty.data.remote.RetrofitFactory
import br.com.chalenge.rickmorty.data.remote.RickMortyApi
import br.com.chalenge.rickmorty.data.repository.CharacterRepositoryImpl
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
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
class DataModule {

    @Provides
    fun providesCharacterRepository(
        rickMortyApi: RickMortyApi,
        characterResultResponseToModelMapper: CharacterResultResponseToModelMapper,
        characterResponseToModelMapper: CharacterResponseToModelMapper
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            rickMortyApi = rickMortyApi,
            characterResultResponseToModelMapper = characterResultResponseToModelMapper,
            characterResponseToModelMapper = characterResponseToModelMapper
        )
    }

    @Provides
    fun providesCharacterResponseToModelMapper(): CharacterResponseToModelMapper {
        return CharacterResponseToModelMapperImpl()
    }

    @Provides
    fun providesCharacterResultResponseToModelMapper(
        characterResponseToModelMapper: CharacterResponseToModelMapper
    ): CharacterResultResponseToModelMapper {
        return CharacterResultResponseToModelMapperImpl(characterResponseToModelMapper)
    }

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

    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClientFactory.provideOkHttpClient()
    }
}