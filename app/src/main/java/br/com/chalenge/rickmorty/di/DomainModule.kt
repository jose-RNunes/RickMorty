package br.com.chalenge.rickmorty.di

import br.com.chalenge.rickmorty.doman.usecase.GetCharacterUseCase
import br.com.chalenge.rickmorty.doman.usecase.GetCharacterUseCaseImpl
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCaseImpl
import br.com.chalenge.rickmorty.doman.usecase.GetLocalCharactersUseCase
import br.com.chalenge.rickmorty.doman.usecase.GetLocalCharactersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun providesGetCharactersUseCase(
        getCharactersUseCaseImpl: GetCharactersUseCaseImpl
    ): GetCharactersUseCase

    @Binds
    abstract fun providesGetLocalCharactersUseCase(
        getLocalCharactersUseCaseImpl: GetLocalCharactersUseCaseImpl
    ): GetLocalCharactersUseCase


    @Binds
    abstract fun providesGetCharacterUseCase(
        getCharacterUseCaseImpl: GetCharacterUseCaseImpl
    ): GetCharacterUseCase
}