package br.com.chalenge.rickmorty.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import br.com.chalenge.rickmorty.ui.character.mapper.CharacterModelToUiModelMapper
import br.com.chalenge.rickmorty.data.repository.paging.PagingMediator
import br.com.chalenge.rickmorty.ui.character.paging.CharacterPaging
import br.com.chalenge.rickmorty.ui.character.uimodel.CharacterUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        if (event is CharacterEvent.GetCharacters) {

        }
    }

    private fun showData(characters: List<CharacterModel>) {
        val charactersUi = characters.map { characterModel ->
            characterModelToUiModelMapper.converter(characterModel)
        }
        _state.update { state -> state.copy(characters = charactersUi) }
    }

    private fun showError(error: Throwable) {
        _state.update { state -> state.copy(showError = true) }
    }
}