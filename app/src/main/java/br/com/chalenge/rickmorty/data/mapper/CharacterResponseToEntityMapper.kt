package br.com.chalenge.rickmorty.data.mapper

import br.com.chalenge.rickmorty.data.entity.CharacterEntity
import br.com.chalenge.rickmorty.data.response.CharacterResponse
import br.com.chalenge.rickmorty.utils.Mapper

interface CharacterResponseToEntityMapper : Mapper<CharacterResponse, CharacterEntity>

class CharacterResponseToEntityMapperImpl : CharacterResponseToEntityMapper {
    override fun converter(from: CharacterResponse): CharacterEntity {
        return CharacterEntity(
            id = from.id,
            name = from.name,
            status = from.status,
            species = from.species,
            type = from.type,
            gender = from.gender,
            image = from.image
        )
    }
}