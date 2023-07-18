package br.com.chalenge.rickmorty.data.response

data class PageInfoResponse(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String? = null
)