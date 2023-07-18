package br.com.chalenge.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import br.com.chalenge.rickmorty.ui.character.CharacterEvent
import br.com.chalenge.rickmorty.ui.character.CharacterViewModel
import br.com.chalenge.rickmorty.ui.character.uimodel.CharacterUiModel
import br.com.chalenge.rickmorty.ui.theme.RickMortyTheme
import coil.compose.AsyncImage
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val characterViewModel: CharacterViewModel = getViewModel()

                    characterViewModel.handleEvent(CharacterEvent.GetCharacters)

                    val characters: LazyPagingItems<CharacterUiModel> =
                        characterViewModel.loadCharacters().collectAsLazyPagingItems()

                    LazyColumn {
                        items(characters.itemCount) { index ->
                            characters[index]?.let { character ->
                                CharacterItem(
                                    character = character,
                                    onSelectCharacter = {}
                                )
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun CharacterItem(
        modifier: Modifier = Modifier,
        character: CharacterUiModel,
        onSelectCharacter: (Int) -> Unit,
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = { onSelectCharacter(character.id) })
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
}