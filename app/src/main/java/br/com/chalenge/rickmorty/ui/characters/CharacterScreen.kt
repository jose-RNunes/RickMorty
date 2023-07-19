package br.com.chalenge.rickmorty.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import br.com.chalenge.rickmorty.ui.characters.uimodel.CharacterUiModel
import coil.compose.AsyncImage

@Composable
fun CharactersScreen(
    characters: LazyPagingItems<CharacterUiModel>,
    onCharacterSelected: (Int) -> Unit
) {
    LazyColumn {
        items(characters.itemCount) { index ->
            characters[index]?.let { character ->
                CharacterItem(
                    character = character,
                    onCharacterSelected = { id ->
                        onCharacterSelected.invoke(id)
                    }
                )
            }
        }
    }
}

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: CharacterUiModel,
    onCharacterSelected: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onCharacterSelected(character.id)
            })
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = character.image,
                modifier = Modifier.size(128.dp),
                contentDescription = null
            )
            Column(Modifier.padding(8.dp)) {
                Text(character.name, style = MaterialTheme.typography.bodyMedium)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(character.statusColor)
                    )
                    Text(character.status)
                }
                Text(character.species)
            }
        }
    }
}