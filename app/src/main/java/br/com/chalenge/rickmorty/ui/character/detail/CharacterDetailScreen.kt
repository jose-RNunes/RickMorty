package br.com.chalenge.rickmorty.ui.character.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.com.chalenge.rickmorty.ui.characters.CharacterItem
import br.com.chalenge.rickmorty.ui.characters.uimodel.CharacterUiModel
import coil.compose.AsyncImage

@Composable
fun CharacterDetailScreen(state: CharacterDetailState,  onBackClick: () -> Unit) {

    if(state.showData() && state.character != null) {
        CharacterDetailItem(characterUiModel = state.character, onBackClick =onBackClick)
    }
}

@Composable
fun CharacterDetailItem(
    modifier: Modifier = Modifier,
    characterUiModel: CharacterUiModel,
    onBackClick: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = characterUiModel.image,
            modifier = Modifier.fillMaxWidth().height(300.dp),
            contentDescription = null
        )
    }
}