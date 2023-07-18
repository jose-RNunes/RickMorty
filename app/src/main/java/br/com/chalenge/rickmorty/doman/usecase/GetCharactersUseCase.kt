package br.com.chalenge.rickmorty.doman.usecase

import androidx.paging.PagingData
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

interface GetCharactersUseCase {

    operator fun invoke(): Flow<PagingData<CharacterModel>>
}

class GetCharacterUseCaseImpl(
    private val characterRepository: CharacterRepository
) : GetCharactersUseCase {
    override fun invoke(): Flow<PagingData<CharacterModel>> {
        return characterRepository.getCharacters()
    }
}