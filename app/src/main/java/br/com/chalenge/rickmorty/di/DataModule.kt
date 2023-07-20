package br.com.chalenge.rickmorty.di

import android.content.Context
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToModelMapperImpl
import br.com.chalenge.rickmorty.data.mapper.CharacterResultResponseToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResultResponseToModelMapperImpl
import br.com.chalenge.rickmorty.data.remote.ErrorInterceptor
import br.com.chalenge.rickmorty.data.remote.ErrorInterceptorImpl
import br.com.chalenge.rickmorty.data.repository.CharacterRepositoryImpl
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun providesContext(@ApplicationContext context: Context): Context

    @Binds
    abstract fun providesCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    abstract fun providesCharacterResponseToModelMapper(
        characterResponseToModelMapperImpl: CharacterResponseToModelMapperImpl
    ): CharacterResponseToModelMapper

    @Binds
    abstract fun providesCharacterResultResponseToModelMapper(
        characterResultResponseToModelMapperImpl: CharacterResultResponseToModelMapperImpl
    ): CharacterResultResponseToModelMapper

    @Binds
    abstract fun bindsErrorInterceptor(errorInterceptor: ErrorInterceptorImpl): ErrorInterceptor
}