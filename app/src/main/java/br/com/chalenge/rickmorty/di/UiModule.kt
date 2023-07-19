package br.com.chalenge.rickmorty.di

import android.content.Context
import br.com.chalenge.rickmorty.ui.characters.mapper.CharaCharacterModelToUiModelMapperImpl
import br.com.chalenge.rickmorty.ui.characters.mapper.CharacterModelToUiModelMapper
import br.com.chalenge.rickmorty.utils.ResourceManager
import br.com.chalenge.rickmorty.utils.ResourceManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {

    @Provides
    fun providesCharacterModelToUiModelMapper(
        resourceManager: ResourceManager
    ): CharacterModelToUiModelMapper {
        return CharaCharacterModelToUiModelMapperImpl(resourceManager)
    }

    @Provides
    fun providesResourceManager(@ApplicationContext context: Context): ResourceManager {
        return ResourceManagerImpl(context)
    }
}