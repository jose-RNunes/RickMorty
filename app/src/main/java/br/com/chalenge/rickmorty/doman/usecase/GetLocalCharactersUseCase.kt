package br.com.chalenge.rickmorty.doman.usecase

import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.PageInfoModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import javax.inject.Inject

interface GetLocalCharactersUseCase {

    suspend operator fun invoke(name: String?, status: String?): List<CharacterModel>
}

class GetLocalCharactersUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetLocalCharactersUseCase {
    override suspend fun invoke(name: String?, status: String?): List<CharacterModel> {
        return characterRepository.getCharacters(name, status)
    }
}