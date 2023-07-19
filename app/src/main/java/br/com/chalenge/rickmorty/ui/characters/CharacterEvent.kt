package br.com.chalenge.rickmorty.ui.characters

sealed interface CharacterEvent {
    data class OnCharacterSelected(val id: Int): CharacterEvent

    object OnNavigated: CharacterEvent
}