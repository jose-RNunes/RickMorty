package br.com.chalenge.rickmorty.doman.usecase

import br.com.chalenge.rickmorty.doman.model.PageInfoModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import javax.inject.Inject

interface GetCharactersUseCase {

    suspend operator fun invoke(page: Int, name: String?, status: String?): PageInfoModel
}

class GetCharactersUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharactersUseCase {
    override suspend fun invoke(page: Int, name: String?, status: String?): PageInfoModel {
        return characterRepository.fetchCharacters(page, name, status)
    }
}