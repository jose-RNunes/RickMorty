package br.com.chalenge.rickmorty.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.chalenge.rickmorty.R
import br.com.chalenge.rickmorty.ui.characters.uimodel.CharacterUiModel
import coil.compose.AsyncImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    state: CharacterState,
    onCharacterSelected: (Int) -> Unit,
    onSearchSelected: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { onSearchSelected.invoke() }) {
                        Icon(Icons.Filled.Search, "searchIcon")
                    }
                }
            )
        }
    ) { paddingValues ->
        CharactersItem(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onCharacterSelected = onCharacterSelected,
            onRetryClick = onRetryClick
        )
    }
}

@ExperimentalCoroutinesApi
@Composable
fun CharactersItem(
    modifier: Modifier = Modifier,
    state: CharacterState,
    onCharacterSelected: (Int) -> Unit,
    onRetryClick: () -> Unit
) {
    val characters = state.characters?.collectAsLazyPagingItems() ?: return

    LazyColumn(modifier = modifier) {
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

    CharacterLoadItem(characters.loadState.refresh, onRetryClick)
}

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: CharacterUiModel,
    onCharacterSelected: (Int) -> Unit
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
                CharacterStatusItem(character = character)
                Text(character.species)
            }
        }
    }
}

@Composable
fun CharacterLoadItem(
    loadState: LoadState,
    onRetryClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (loadState is LoadState.Loading) {
            CircularProgressIndicator()
        }

        if (loadState is LoadState.Error) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = (loadState as? LoadState.Error)?.error?.message.orEmpty())

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { onRetryClick.invoke() }) {
                    Text(text = stringResource(id = R.string.retry_action))
                }
            }
        }
    }
}

@Composable
fun CharacterStatusItem(character: CharacterUiModel) {
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
}