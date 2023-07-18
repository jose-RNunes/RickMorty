package br.com.chalenge.rickmorty.doman.model

import br.com.chalenge.rickmorty.utils.capitalizeText

enum class CharacterStatusType {
    DEAD,
    ALIVE,
    UNKNOWN;

    companion object {
        fun find(characterStatus: String): CharacterStatusType {
            return values().firstOrNull { status ->
                status.name == characterStatus.capitalizeText()
            } ?: UNKNOWN
        }
    }
}