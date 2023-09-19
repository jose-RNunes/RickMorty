package br.com.chalenge.rickmorty.doman.repository

import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.PageInfoModel

interface CharacterRepository {

    suspend fun fetchCharacters(page: Int, name: String?, status: String?): PageInfoModel

    suspend fun getCharacters(name:String?, status: String?): List<CharacterModel>

    suspend fun getCharacter(id: Int): CharacterModel
}