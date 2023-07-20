package br.com.chalenge.rickmorty.data.mapper

import br.com.chalenge.rickmorty.data.response.CharacterResponse
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.CharacterStatusType
import br.com.chalenge.rickmorty.doman.model.GenderType
import br.com.chalenge.rickmorty.utils.Mapper
import javax.inject.Inject

interface CharacterResponseToModelMapper : Mapper<CharacterResponse, CharacterModel>

class CharacterResponseToModelMapperImpl @Inject constructor(): CharacterResponseToModelMapper {
    override fun converter(from: CharacterResponse): CharacterModel {
        return CharacterModel(
            id = from.id,
            name = from.name,
            status = CharacterStatusType.find(from.status),
            species = from.species,
            type = from.type,
            gender = GenderType.find(from.gender),
            image = from.image
        )
    }
}