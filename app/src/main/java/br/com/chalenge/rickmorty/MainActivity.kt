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
import br.com.chalenge.rickmorty.ui.characters.CharacterEvent
import br.com.chalenge.rickmorty.ui.characters.CharacterViewModel
import br.com.chalenge.rickmorty.ui.characters.CharactersScreen
import br.com.chalenge.rickmorty.ui.navigation.AppRoute
import br.com.chalenge.rickmorty.ui.navigation.ScreenRoute
import br.com.chalenge.rickmorty.ui.theme.RickMortyTheme
import dagger.hilt.android.AndroidEntryPoint

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
                    navigationApp()
                }
            }
        }
    }

    @Composable
    fun navigationApp() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.CHARACTER_SCREEN.route
        ) {
            charactersScreen(navController)
            characterDetailScreen(navController)
        }
    }

    private fun NavGraphBuilder.charactersScreen(navController: NavHostController) {
        composable(ScreenRoute.CHARACTER_SCREEN.route) {
            val characterViewModel = hiltViewModel<CharacterViewModel>()

            val state by characterViewModel.state.collectAsState()

            characterViewModel.handleEvent(CharacterEvent.GetCharacters)

            CharactersScreen(state) { id ->
                characterViewModel.handleEvent(CharacterEvent.OnCharacterSelected(id))
            }

            if (state.navigateToDetail) {
                characterViewModel.handleEvent(CharacterEvent.OnNavigated)
                val safeCharacterId = state.characterSelectedId ?: 0
                navController.navigate(
                    AppRoute.CharacterDetailsScreen(safeCharacterId).getCalculatedRoute()
                )
            }
        }
    }

    private fun NavGraphBuilder.characterDetailScreen(navHostController: NavHostController) {
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
                    onBackClick = { }
                )
            }
        }
    }
}