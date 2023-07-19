package br.com.chalenge.rickmorty.ui.navigation

sealed class AppRoute {

    abstract fun getCalculatedRoute(): String

    data class CharacterDetailsScreen(val id: Int) : AppRoute() {
        override fun getCalculatedRoute(): String {
            return "/detail/$id"
        }
    }
}

enum class ScreenRoute(val route: String) {
    CHARACTER_SCREEN("/characters"),
    CHARACTER_DETAIL_SCREEN("/detail/{id}")
}