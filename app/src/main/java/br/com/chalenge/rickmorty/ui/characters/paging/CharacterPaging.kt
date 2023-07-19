package br.com.chalenge.rickmorty.ui.characters.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import javax.inject.Inject

class CharacterPaging @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) {

    fun getCharacters(): PagingSource<Int, CharacterModel> {
        return object : PagingSource<Int, CharacterModel>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
                val pageNumber = params.key ?: 0

                val pageInfoModel = getCharactersUseCase(pageNumber)

                val prevKey = if (pageNumber > 0) pageNumber - 1 else null
                val nextKey = if (pageInfoModel.next != null) pageNumber + 1 else null

                return LoadResult.Page(
                    data = pageInfoModel.characters,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }

            override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                }
            }
        }
    }
}