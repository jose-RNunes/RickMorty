package br.com.chalenge.rickmorty.ui.characters.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import br.com.chalenge.rickmorty.doman.usecase.GetLocalCharactersUseCase
import javax.inject.Inject

class CharacterPaging @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getLocalCharactersUseCase: GetLocalCharactersUseCase
) {

    fun getCharacters(name: String? = null, status: String? = null): PagingSource<Int, CharacterModel> {
        return object : PagingSource<Int, CharacterModel>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
                return try {
                    val pageNumber = params.key ?: 0

                    val pageInfoModel = getCharactersUseCase(pageNumber, name, status)

                    val prevKey = if (pageNumber > 0) pageNumber - 1 else null
                    val nextKey = if (pageInfoModel.next != null) pageNumber + 1 else null

                    return LoadResult.Page(
                        data = pageInfoModel.characters,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                } catch (e: Exception) {
                    val localCharacters = getLocalCharactersUseCase(name, status)
                    if (getLocalCharactersUseCase(name, status).isNotEmpty()) {
                        return LoadResult.Page(
                            data = localCharacters,
                            prevKey = null,
                            nextKey = null
                        )
                    } else {
                        LoadResult.Error(e)
                    }
                }
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