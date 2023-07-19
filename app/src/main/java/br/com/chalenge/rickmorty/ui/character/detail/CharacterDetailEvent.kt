package br.com.chalenge.rickmorty.ui.character.detail

sealed interface CharacterDetailEvent {

    data class GetCharacter(val id: Int): CharacterDetailEvent
}