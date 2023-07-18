package br.com.chalenge.rickmorty.data.mapper

import br.com.chalenge.rickmorty.data.response.CharacterResponse
import br.com.chalenge.rickmorty.data.response.CharacterResultResponse
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.PageInfoModel
import br.com.chalenge.rickmorty.utils.Mapper

interface CharacterResultResponseToModelMapper : Mapper<CharacterResultResponse, PageInfoModel>

class CharacterResultResponseToModelMapperImpl(
    private val characterResponseToModelMapper: CharacterResponseToModelMapper
) : CharacterResultResponseToModelMapper {
    override fun converter(from: CharacterResultResponse): PageInfoModel {
        val pageResponse = from.info
        return PageInfoModel(
            count = pageResponse.count,
            pages = pageResponse.pages,
            next = pageResponse.next,
            prev = pageResponse.prev,
            characters = from.results.map { characterResponse ->
                characterResponseToModelMapper.converter(characterResponse)
            }
        )
    }
}