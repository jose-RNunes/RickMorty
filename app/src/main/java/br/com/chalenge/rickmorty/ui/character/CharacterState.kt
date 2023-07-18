package br.com.chalenge.rickmorty.ui.character

import br.com.chalenge.rickmorty.ui.character.uimodel.CharacterUiModel

data class CharacterState(
    val characters: List<CharacterUiModel> = emptyList(),
    val characterSelected: CharacterUiModel? = null,
    val showError: Boolean = false
)