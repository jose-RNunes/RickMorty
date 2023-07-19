package br.com.chalenge.rickmorty.doman.repository

import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.PageInfoModel

interface CharacterRepository {

    suspend fun fetchCharacters(page: Int): PageInfoModel

    suspend fun fetchCharacter(id: Int): CharacterModel
}