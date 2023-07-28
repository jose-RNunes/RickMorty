package br.com.chalenge.rickmorty.ui.character.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.chalenge.rickmorty.ui.characters.CharacterStatusItem
import br.com.chalenge.rickmorty.ui.characters.uimodel.CharacterUiModel
import coil.compose.AsyncImage

@Composable
fun CharacterDetailScreen(state: CharacterDetailState, onBackClick: () -> Unit) {
    if (state.showData() && state.character != null) {
        CharacterDetailItem(
            character = state.character,
            onBackClick = onBackClick
        )
    }
}

@Composable
fun CharacterDetailItem(
    modifier: Modifier = Modifier,
    character: CharacterUiModel,
    onBackClick: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            model = character.image,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)),
            contentDescription = null
        )

        Text(
            text = character.name,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CharacterStatusItem(character = character)

            Text(text = character.species)

            Text(text = character.location)

            Text(text = character.origin)
        }
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            shape = CircleShape
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.padding(4.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "navigate back"
                )
            }
        }
    }
}