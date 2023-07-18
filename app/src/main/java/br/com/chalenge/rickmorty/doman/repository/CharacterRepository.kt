package br.com.chalenge.rickmorty.doman.repository

import br.com.chalenge.rickmorty.doman.model.PageInfoModel

interface CharacterRepository {

    suspend fun getCharacters(page: Int): PageInfoModel
}