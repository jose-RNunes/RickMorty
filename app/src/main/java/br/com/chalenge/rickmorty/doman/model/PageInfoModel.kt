package br.com.chalenge.rickmorty.doman.model

data class PageInfoModel(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?,
    val characters: List<CharacterModel>
)