package br.com.chalenge.rickmorty.data.remote

import br.com.chalenge.rickmorty.data.response.CharacterResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyApi {

    @GET("character/")
    suspend fun fetchCharacters(@Query("page") page: Int): CharacterResultResponse
}