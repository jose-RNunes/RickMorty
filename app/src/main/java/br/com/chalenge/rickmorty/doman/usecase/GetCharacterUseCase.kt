package br.com.chalenge.rickmorty.doman.usecase

import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository

interface GetCharacterUseCase {

    suspend operator fun invoke(id: Int): CharacterModel
}

class GetCharacterUseCaseImpl(
    private val characterRepository: CharacterRepository
) : GetCharacterUseCase {

    override suspend fun invoke(id: Int): CharacterModel {
        return characterRepository.fetchCharacter(id)
    }
}