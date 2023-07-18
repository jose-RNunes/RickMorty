package br.com.chalenge.rickmorty.data.response

data class CharacterResultResponse(
    val info: PageInfoResponse,
    val results: List<CharacterResponse>
)