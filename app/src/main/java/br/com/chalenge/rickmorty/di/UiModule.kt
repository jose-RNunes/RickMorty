package br.com.chalenge.rickmorty.di

import br.com.chalenge.rickmorty.ui.characters.mapper.CharaCharacterModelToUiModelMapperImpl
import br.com.chalenge.rickmorty.ui.characters.mapper.CharacterModelToUiModelMapper
import br.com.chalenge.rickmorty.utils.ResourceManager
import br.com.chalenge.rickmorty.utils.ResourceManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UiModule {

    @Binds
   abstract fun providesCharacterModelToUiModelMapper(
        charaCharacterModelToUiModelMapperImpl: CharaCharacterModelToUiModelMapperImpl
    ): CharacterModelToUiModelMapper

    @Binds
    abstract fun providesResourceManager(resourceManagerImpl: ResourceManagerImpl): ResourceManager
}