package br.com.chalenge.rickmorty.data.response

data class CharacterResponse(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val origin: OriginResponse,
    val location: LocationResponse
)
