package br.com.chalenge.rickmorty.ui.characters.mapper

import androidx.compose.ui.graphics.Color
import br.com.chalenge.rickmorty.R
import br.com.chalenge.rickmorty.doman.model.CharacterModel
import br.com.chalenge.rickmorty.doman.model.CharacterStatusType
import br.com.chalenge.rickmorty.ui.characters.uimodel.CharacterUiModel
import br.com.chalenge.rickmorty.utils.Mapper
import br.com.chalenge.rickmorty.utils.ResourceManager
import br.com.chalenge.rickmorty.utils.capitalizeText
import javax.inject.Inject

interface CharacterModelToUiModelMapper : Mapper<CharacterModel, CharacterUiModel>

class CharaCharacterModelToUiModelMapperImpl @Inject constructor(
    private val resourceManager: ResourceManager
) : CharacterModelToUiModelMapper {
    override fun converter(from: CharacterModel): CharacterUiModel {
        return CharacterUiModel(
            id = from.id,
            name = from.name,
            status = from.status.name.capitalizeText(),
            statusColor = getStatusColor(from.status),
            species = resourceManager.getString(R.string.species_gender)
                .format(
                    from.species,
                    from.gender.name.capitalizeText()
                ),
            image = from.image,
            type = from.type,
            location = resourceManager.getString(R.string.location)
                .format(from.location),
            origin = resourceManager.getString(R.string.origin)
                .format(from.origin)
        )
    }

    private fun getStatusColor(characterStatusType: CharacterStatusType): Color {
        return when (characterStatusType) {
            CharacterStatusType.ALIVE -> Color.Green
            CharacterStatusType.DEAD -> Color.Red
            CharacterStatusType.UNKNOWN -> Color.DarkGray
        }
    }
}