package br.com.chalenge.rickmorty.data.mapper

import br.com.chalenge.rickmorty.data.entity.CharacterEntity
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.CharacterStatusType
import br.com.chalenge.rickmorty.doman.model.GenderType
import br.com.chalenge.rickmorty.utils.Mapper

interface CharacterEntityToModelMapper : Mapper<CharacterEntity, CharacterModel>

class CharacterEntityToModelMapperImpl : CharacterEntityToModelMapper {
    override fun converter(from: CharacterEntity): CharacterModel {
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