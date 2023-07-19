package br.com.chalenge.rickmorty.doman.usecase

import br.com.chalenge.rickmorty.doman.model.PageInfoModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository

interface GetCharactersUseCase {

    suspend operator fun invoke(page: Int): PageInfoModel
}

class GetCharactersUseCaseImpl(
    private val characterRepository: CharacterRepository
) : GetCharactersUseCase {
    override suspend fun invoke(page: Int): PageInfoModel {
        return characterRepository.fetchCharacters(page)
    }
}