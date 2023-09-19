package br.com.chalenge.rickmorty.data.repository

import br.com.chalenge.rickmorty.data.mapper.CharacterEntityToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToEntityMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResultResponseToModelMapper
import br.com.chalenge.rickmorty.data.remote.RickMortyApi
import br.com.chalenge.rickmorty.data.response.CharacterNotFound
import br.com.chalenge.rickmorty.database.CharacterDao
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.PageInfoModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val rickMortyApi: RickMortyApi,
    private val characterDao: CharacterDao,
    private val characterResultResponseToModelMapper: CharacterResultResponseToModelMapper,
    private val characterResponseToEntityMapper: CharacterResponseToEntityMapper,
    private val characterEntityToModelMapper: CharacterEntityToModelMapper
) : CharacterRepository {

    override suspend fun fetchCharacters(page: Int, name: String?, status: String?): PageInfoModel {
        val characters = rickMortyApi.fetchCharacters(page, name.orEmpty(), status.orEmpty().lowercase())

        val charactersEntity = characters.results.map { characterResponse ->
            characterResponseToEntityMapper.converter(characterResponse)
        }

        characterDao.saveCharacters(charactersEntity)

        return characterResultResponseToModelMapper.converter(characters)
    }

    override suspend fun getCharacters(name: String?, status: String?): List<CharacterModel> {
        return characterDao.getCharacters(name).map { characterEntity ->
            characterEntityToModelMapper.converter(characterEntity)
        }
    }

    override suspend fun getCharacter(id: Int): CharacterModel {
        val characterEntity = characterDao.getCharacter(id)

        return characterEntity?.let { entity ->
            characterEntityToModelMapper.converter(entity)
        } ?: throw CharacterNotFound(String())
    }
}