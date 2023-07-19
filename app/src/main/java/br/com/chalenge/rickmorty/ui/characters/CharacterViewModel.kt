package br.com.chalenge.rickmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import br.com.chalenge.rickmorty.ui.characters.mapper.CharacterModelToUiModelMapper
import br.com.chalenge.rickmorty.ui.characters.paging.CharacterPaging
import br.com.chalenge.rickmorty.ui.characters.uimodel.CharacterUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class CharacterViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterModelToUiModelMapper: CharacterModelToUiModelMapper
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterState())
    val state = _state.asStateFlow()

    fun loadCharacters(): Flow<PagingData<CharacterUiModel>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
            )
        ) {
            CharacterPaging(getCharactersUseCase).getCharacters()
        }.flow.map { paging ->
            paging.map { model -> characterModelToUiModelMapper.converter(model) }
        }.cachedIn(viewModelScope)
    }

    fun handleEvent(event: CharacterEvent) {
        when(event) {
          is CharacterEvent.OnCharacterSelected -> onCharacterSelected(event.id)
          is CharacterEvent.OnNavigated -> onNavigated()
        }
    }

    private fun onCharacterSelected(id: Int) {
        _state.update { state -> state.copy(characterSelectedId = id, navigateToDetail = true) }
    }

    private fun onNavigated() {
        _state.update { state -> state.onNavigated() }
    }
}