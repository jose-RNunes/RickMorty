package br.com.chalenge.rickmorty.ui.characters.uimodel

import androidx.compose.ui.graphics.Color

data class CharacterUiModel(
    val id: Int,
    val name: String,
    val status: String,
    val statusColor: Color,
    val species: String,
    val type: String,
    val image: String
)
