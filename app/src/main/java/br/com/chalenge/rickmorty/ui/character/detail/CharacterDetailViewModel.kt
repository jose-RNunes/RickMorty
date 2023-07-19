package br.com.chalenge.rickmorty.ui.character.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.usecase.GetCharacterUseCase
import br.com.chalenge.rickmorty.ui.characters.CharacterState
import br.com.chalenge.rickmorty.ui.characters.mapper.CharacterModelToUiModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val characterModelToUiModelMapper: CharacterModelToUiModelMapper
    ): ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    fun handleEvent(event: CharacterDetailEvent) {
        if(event is CharacterDetailEvent.GetCharacter) {
            getCharacter(event.id)
        }
    }

    private fun getCharacter(id: Int) {
        _state.update { state -> state.showLoading() }
        viewModelScope.launch {
            runCatching {
                getCharacterUseCase(id)
            }.onSuccess(::showData).onFailure(::showError)
        }
    }

    private fun showData(character: CharacterModel) {
        val charactersUi = characterModelToUiModelMapper.converter(character)
        _state.update { state -> state.setCharacter(charactersUi) }
    }

    private fun showError(error: Throwable) {
        _state.update { state -> state.setShowError() }
    }
}