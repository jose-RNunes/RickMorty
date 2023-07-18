package br.com.chalenge.rickmorty.doman.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: CharacterStatusType,
    val species: String,
    val type: String,
    val gender: GenderType,
    val image: String
)