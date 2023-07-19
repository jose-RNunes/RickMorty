package br.com.chalenge.rickmorty.di

import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import br.com.chalenge.rickmorty.doman.usecase.GetCharacterUseCase
import br.com.chalenge.rickmorty.doman.usecase.GetCharacterUseCaseImpl
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

   @Provides
   fun providesGetCharactersUseCase(characterRepository: CharacterRepository): GetCharactersUseCase {
       return GetCharactersUseCaseImpl(characterRepository)
   }

    @Provides
    fun providesGetCharacterUseCase(characterRepository: CharacterRepository): GetCharacterUseCase {
        return GetCharacterUseCaseImpl(characterRepository)
    }
}