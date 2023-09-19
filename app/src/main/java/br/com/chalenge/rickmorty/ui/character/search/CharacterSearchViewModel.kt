package br.com.chalenge.rickmorty.ui.character.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import br.com.chalenge.rickmorty.ui.characters.CharacterState
import br.com.chalenge.rickmorty.ui.characters.mapper.CharacterModelToUiModelMapper
import br.com.chalenge.rickmorty.ui.characters.paging.CharacterPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterSearchViewModel @Inject constructor(
    private val characterModelToUiModelMapper: CharacterModelToUiModelMapper,
    private val characterPaging: CharacterPaging
) : ViewModel() {

    private var job: Job? = null

    private val _state = MutableStateFlow(CharacterState())
    val state = _state.asStateFlow()

    fun handleEvent(event: CharacterSearchEvent) {
        when (event) {
            is CharacterSearchEvent.SearchCharacter -> getCharacters(event.searchCharacter)
            is CharacterSearchEvent.OnCharacterSelected -> onCharacterSelected(event.id)
            is CharacterSearchEvent.OnNavigated -> onNavigated()
            is CharacterSearchEvent.OnRetry -> getCharacters(state.value.characterSearch)
            is CharacterSearchEvent.OnStatusSelected -> onStatusSelected(event.status)
        }
    }

    private fun getCharacters(searchCharacter: String) {
        job?.cancel()
        job = viewModelScope.launch {
            _state.update { state -> state.setCharacterSearch(searchCharacter) }
            if (searchCharacter.length > SEARCH_MIN_LENGTH) delay(DELAY_TIME)
            val characters = Pager(
                PagingConfig(
                    pageSize = MAX_ITEM_PER_PAGE,
                    enablePlaceholders = true,
                )
            ) {
                characterPaging.getCharacters(searchCharacter, state.value.onStatusSelected)
            }.flow
                .map { paging ->
                    paging.map { model -> characterModelToUiModelMapper.converter(model) }
                }
            _state.update { state -> state.setCharacters(characters) }
        }
    }

    private fun onCharacterSelected(id: Int) {
        _state.update { state -> state.onCharacterSelected(id) }
    }

    private fun onNavigated() {
        _state.update { state -> state.onNavigated() }
    }

    private fun onStatusSelected(status: String?) {
        if (status != null) {
            _state.update { state -> state.onStatusSelected(status) }
            getCharacters(state.value.characterSearch)
        }
    }

    private companion object {
        const val MAX_ITEM_PER_PAGE = 20
        const val DELAY_TIME = 500L
        const val SEARCH_MIN_LENGTH = 3
    }
}