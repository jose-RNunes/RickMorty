package br.com.chalenge.rickmorty.ui.characters

sealed interface CharacterEvent {

    object GetCharacters: CharacterEvent

    data class OnCharacterSelected(val id: Int): CharacterEvent

    object OnNavigateToSearch: CharacterEvent

    object OnNavigated: CharacterEvent

    object OnRetry: CharacterEvent
}