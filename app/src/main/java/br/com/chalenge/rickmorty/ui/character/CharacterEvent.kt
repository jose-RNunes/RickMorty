package br.com.chalenge.rickmorty.ui.character

sealed interface CharacterEvent {
    object GetCharacters: CharacterEvent
}