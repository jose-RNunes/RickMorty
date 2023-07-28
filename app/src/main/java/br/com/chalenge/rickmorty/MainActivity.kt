package br.com.chalenge.rickmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.chalenge.rickmorty.ui.character.detail.CharacterDetailEvent
import br.com.chalenge.rickmorty.ui.character.detail.CharacterDetailScreen
import br.com.chalenge.rickmorty.ui.character.detail.CharacterDetailViewModel
import br.com.chalenge.rickmorty.ui.character.search.CharacterSearchEvent
import br.com.chalenge.rickmorty.ui.character.search.CharacterSearchScreen
import br.com.chalenge.rickmorty.ui.character.search.CharacterSearchViewModel
import br.com.chalenge.rickmorty.ui.characters.CharacterEvent
import br.com.chalenge.rickmorty.ui.characters.CharacterViewModel
import br.com.chalenge.rickmorty.ui.characters.CharactersScreen
import br.com.chalenge.rickmorty.ui.navigation.AppRoute
import br.com.chalenge.rickmorty.ui.navigation.ScreenRoute
import br.com.chalenge.rickmorty.ui.theme.RickMortyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
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
                    NavigationApp()
                }
            }
        }
    }

    @Composable
    fun NavigationApp() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.CHARACTER_SCREEN.route
        ) {
            charactersScreen(navController)
            characterDetailScreen(navController)
            characterSearchScreen(navController)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun NavGraphBuilder.charactersScreen(navController: NavHostController) {
        composable(ScreenRoute.CHARACTER_SCREEN.route) {
            val characterViewModel = hiltViewModel<CharacterViewModel>()

            val state by characterViewModel.state.collectAsState()

            CharactersScreen(
                state = state,
                onCharacterSelected = { id ->
                    characterViewModel.handleEvent(CharacterEvent.OnCharacterSelected(id))
                },
                onSearchSelected = {
                    characterViewModel.handleEvent(CharacterEvent.OnNavigateToSearch)
                }
            )

            if (state.navigateToDetail) {
                characterViewModel.handleEvent(CharacterEvent.OnNavigated)
                val safeCharacterId = state.characterSelectedId ?: 0
                navController.navigate(
                    AppRoute.CharacterDetailsScreen(safeCharacterId).getCalculatedRoute()
                )
            }

            if (state.navigateToSearch) {
                characterViewModel.handleEvent(CharacterEvent.OnNavigated)
                navController.navigate(ScreenRoute.CHARACTER_SEARCH_SCREEN.route)
            }

            characterViewModel.handleEvent(CharacterEvent.GetCharacters)
        }
    }

    private fun NavGraphBuilder.characterDetailScreen(navController: NavHostController) {
        composable(
            ScreenRoute.CHARACTER_DETAIL_SCREEN.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0

            val characterDetailViewModel = hiltViewModel<CharacterDetailViewModel>()

            val state by characterDetailViewModel.state.collectAsState()

            LaunchedEffect(key1 = id) {
                characterDetailViewModel.handleEvent(CharacterDetailEvent.GetCharacter(id = id))
            }

            if (state.showData()) {
                CharacterDetailScreen(
                    state = state,
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun NavGraphBuilder.characterSearchScreen(navController: NavHostController) {
        composable(
            ScreenRoute.CHARACTER_SEARCH_SCREEN.route
        ) {
            val characterSearchViewModel = hiltViewModel<CharacterSearchViewModel>()

            val state by characterSearchViewModel.state.collectAsState()

            CharacterSearchScreen(
                state = state,
                onBackClick = { navController.popBackStack() },
                onCharacterSelected = { id ->
                    characterSearchViewModel.handleEvent(CharacterSearchEvent.OnCharacterSelected(id))
                },
                onValueChange = { value ->
                    characterSearchViewModel.handleEvent(CharacterSearchEvent.SearchCharacter(value))
                }
            )

            if (state.navigateToDetail) {
                characterSearchViewModel.handleEvent(CharacterSearchEvent.OnNavigated)
                val safeCharacterId = state.characterSelectedId ?: 0
                navController.navigate(
                    AppRoute.CharacterDetailsScreen(safeCharacterId).getCalculatedRoute()
                )
            }
        }
    }
}