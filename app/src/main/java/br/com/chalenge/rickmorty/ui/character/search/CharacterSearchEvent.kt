package br.com.chalenge.rickmorty.ui.character.search

sealed interface CharacterSearchEvent {
    data class SearchCharacter(val searchCharacter: String) : CharacterSearchEvent

    data class OnCharacterSelected(val id: Int) : CharacterSearchEvent

    object OnNavigated : CharacterSearchEvent

    object OnRetry: CharacterSearchEvent

    data class OnStatusSelected(val status: String?): CharacterSearchEvent
}