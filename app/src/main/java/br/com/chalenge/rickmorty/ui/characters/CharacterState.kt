package br.com.chalenge.rickmorty.ui.characters

import androidx.paging.PagingData
import br.com.chalenge.rickmorty.ui.characters.uimodel.CharacterUiModel
import kotlinx.coroutines.flow.Flow

data class CharacterState(
    val alreadyStarted: Boolean = false,
    val characterSelectedId: Int? = null,
    val navigateToDetail: Boolean = false,
    val navigateToSearch: Boolean = false,
    val characterSearch: String = "",
    val characters: Flow<PagingData<CharacterUiModel>>? = null
) {
    fun setCharacterSearch(characterSearch: String) = copy(characterSearch = characterSearch)

    fun setCharacters(
        characters: Flow<PagingData<CharacterUiModel>>
    ) = copy(
        alreadyStarted = true,
        characters = characters
    )

    fun onCharacterSelected(id: Int) = copy(characterSelectedId = id, navigateToDetail = true)

    fun onNavigateToSearch() = copy(navigateToSearch = true)

    fun onNavigated() = copy(
        navigateToDetail = false,
        navigateToSearch = false,
        characterSelectedId = null
    )
}