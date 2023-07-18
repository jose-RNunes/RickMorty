package br.com.chalenge.rickmorty.data.repository

import br.com.chalenge.rickmorty.data.mapper.CharacterEntityToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToEntityMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResultResponseToModelMapper
import br.com.chalenge.rickmorty.data.remote.RickMortyApi
import br.com.chalenge.rickmorty.database.AppDataBase
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.PageInfoModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val rickMortyApi: RickMortyApi,
    private val characterEntityToModelMapper: CharacterEntityToModelMapper,
    private val characterResultResponseToModelMapper: CharacterResultResponseToModelMapper
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): PageInfoModel {
        val characters = rickMortyApi.fetchCharacters(page)
        return characterResultResponseToModelMapper.converter(characters)
    }
}