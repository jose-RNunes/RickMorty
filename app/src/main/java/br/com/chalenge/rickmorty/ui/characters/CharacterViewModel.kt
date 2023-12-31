package br.com.chalenge.rickmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import br.com.chalenge.rickmorty.ui.characters.mapper.CharacterModelToUiModelMapper
import br.com.chalenge.rickmorty.ui.characters.paging.CharacterPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterModelToUiModelMapper: CharacterModelToUiModelMapper,
    private val characterPaging: CharacterPaging
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterState())
    val state = _state.asStateFlow()

    fun handleEvent(event: CharacterEvent) {
        when (event) {
            is CharacterEvent.GetCharacters -> getCharacters()
            is CharacterEvent.OnCharacterSelected -> onCharacterSelected(event.id)
            is CharacterEvent.OnNavigateToSearch -> onNavigateToSearch()
            is CharacterEvent.OnNavigated -> onNavigated()
            is CharacterEvent.OnRetry -> onRetry()
        }
    }

    private fun getCharacters() {
        if (_state.value.alreadyStarted) return

        getCharactersInternal()
    }

    private fun getCharactersInternal() {
        val characters = Pager(
            PagingConfig(
                pageSize = MAX_ITEM_PER_PAGE,
                enablePlaceholders = true
            )
        ) {
            characterPaging.getCharacters()
        }.flow
            .map { paging ->
                paging.map { model -> characterModelToUiModelMapper.converter(model) }
            }
            .cachedIn(viewModelScope)
        _state.update { state -> state.setCharacters(characters) }
    }

    private fun onCharacterSelected(id: Int) {
        _state.update { state -> state.onCharacterSelected(id) }
    }

    private fun onNavigateToSearch() {
        _state.update { state -> state.onNavigateToSearch() }
    }

    private fun onNavigated() {
        _state.update { state -> state.onNavigated() }
    }

    private fun onRetry() {
        getCharactersInternal()
    }

    private companion object {
        const val MAX_ITEM_PER_PAGE = 20
    }
}