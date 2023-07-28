package br.com.chalenge.rickmorty.ui.character.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.chalenge.rickmorty.ui.characters.CharacterState
import br.com.chalenge.rickmorty.ui.characters.CharactersItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@Composable
fun CharacterSearchScreen(
    state: CharacterState,
    onBackClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onCharacterSelected: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CustomSearchBar(
                value = state.characterSearch,
                placeholder = "Search Characters",
                onBackClick = onBackClick,
                onValueChange = { name ->
                    onValueChange(name)
                }
            )
        }
    ) { paddingValue ->
        CharactersItem(
            modifier = Modifier.padding(paddingValue),
            state = state,
            onCharacterSelected = onCharacterSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    value: String,
    placeholder: String,
    onBackClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    val requester = remember { FocusRequester() }
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
            TextField(
                value = value,
                onValueChange = { name ->
                    onValueChange(name)
                },
                placeholder = {
                    Text(text = placeholder)
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(
                        focusRequester = requester
                    ),
                trailingIcon = {
                    if (value.isNotBlank()) {
                        IconButton(onClick = {
                            onValueChange("")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "clear Search",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
        }

        Divider(modifier = Modifier.fillMaxWidth())
    }
    SideEffect {
        requester.requestFocus()
    }
}