package br.com.chalenge.rickmorty.data.remote

import br.com.chalenge.rickmorty.data.response.CharacterResponse
import br.com.chalenge.rickmorty.data.response.CharacterResultResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickMortyApi {

    @GET("character/")
    suspend fun fetchCharacters(
        @Query("page") page: Int,
        @Query("name") name: String
    ): CharacterResultResponse

    @GET("character/{id}")
    suspend fun fetchCharacter(@Path("id") id: Int): CharacterResponse
}