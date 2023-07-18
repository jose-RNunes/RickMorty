package br.com.chalenge.rickmorty.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import br.com.chalenge.rickmorty.data.entity.CharacterEntity
import br.com.chalenge.rickmorty.data.remote.RickMortyApi
import br.com.chalenge.rickmorty.database.AppDataBase
import br.com.chalenge.rickmorty.database.CharacterDao
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PagingMediator(
    private val appDataBase: AppDataBase,
    private val rickMortyApi: RickMortyApi
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>,
    ): MediatorResult {
        return try {

            val loadKey: Int = when (loadType) {
                LoadType.REFRESH -> PAGE_START_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> (state.getPageNumber() ?: 0) + 1
            }

            val characters = rickMortyApi.fetchCharacters(loadKey)

            MediatorResult.Success(endOfPaginationReached = characters.results.isEmpty())

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }
    }

    private fun PagingState<Int, CharacterEntity>.getPageNumber(): Int? {
        val last = lastItemOrNull() ?: return null
        val position = last.id
        val size = config.pageSize
        val currentPage = (position / size) + (if (position % size == 0) -1 else 0)
        return currentPage + PAGE_START_INDEX
    }

    private companion object {
        const val PAGE_START_INDEX: Int = 1
    }
}