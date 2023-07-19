package br.com.chalenge.rickmorty.ui.characters

data class CharacterState(
    val characterSelectedId: Int? = null,
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val navigateToDetail: Boolean = false
) {

    fun onCharacterSelected(id: Int) = copy(characterSelectedId = id, navigateToDetail = true)

    fun onNavigated() = copy(navigateToDetail = false, characterSelectedId = null)
}