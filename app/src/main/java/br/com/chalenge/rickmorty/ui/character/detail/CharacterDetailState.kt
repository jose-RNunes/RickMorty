package br.com.chalenge.rickmorty.ui.character.detail

import br.com.chalenge.rickmorty.ui.characters.uimodel.CharacterUiModel

data class CharacterDetailState(
    val character: CharacterUiModel? = null,
    val showLoading: Boolean = false,
    val showError: Boolean = false
) {

    fun showLoading() = copy(showLoading = true)

    fun showData() = character != null

    fun showError() = showLoading.not() && showError

    fun setCharacter(character: CharacterUiModel) = copy(
        showLoading = false,
        character = character,
        showError = false
    )

    fun setShowError() = copy(showLoading = false, showError = true)
}