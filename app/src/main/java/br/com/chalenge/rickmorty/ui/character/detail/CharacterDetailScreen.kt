package br.com.chalenge.rickmorty.ui.character.detail

import androidx.compose.runtime.Composable
import br.com.chalenge.rickmorty.ui.characters.CharacterItem

@Composable
fun CharacterDetailScreen(state: CharacterDetailState,  onBackClick: () -> Unit) {

    if(state.showData() && state.character != null) {
        CharacterItem(character = state.character, onCharacterSelected = {})
    }
}