package br.com.chalenge.rickmorty.doman.repository

import androidx.paging.PagingData
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<PagingData<CharacterModel>>
}