package br.com.chalenge.rickmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.chalenge.rickmorty.data.mapper.CharacterEntityToModelMapper
import br.com.chalenge.rickmorty.data.repository.paging.PagingMediator
import br.com.chalenge.rickmorty.database.AppDataBase
import br.com.chalenge.rickmorty.database.CharacterDao
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class CharacterRepositoryImpl(
    private val pagingMediator: PagingMediator,
    private val appDataBase: AppDataBase,
    private val characterEntityToModelMapper: CharacterEntityToModelMapper
) : CharacterRepository {

    override fun getCharacters(): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
            ),
            remoteMediator = pagingMediator,
            pagingSourceFactory = { appDataBase.characterDao().getPagedCharacters() }
        ).flow.map { paging ->
            paging.map { entity -> characterEntityToModelMapper.converter(entity) }
        }
    }
}