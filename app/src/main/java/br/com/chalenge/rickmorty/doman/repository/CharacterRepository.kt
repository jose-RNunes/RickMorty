package br.com.chalenge.rickmorty.doman.repository

import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.PageInfoModel

interface CharacterRepository {

    suspend fun fetchCharacters(page: Int, name: String?): PageInfoModel

    suspend fun getCharacters(name:String?): List<CharacterModel>

    suspend fun fetchCharacter(id: Int): CharacterModel
}