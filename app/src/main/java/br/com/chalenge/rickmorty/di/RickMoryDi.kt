package br.com.chalenge.rickmorty.di

import br.com.chalenge.rickmorty.data.mapper.CharacterEntityToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterEntityToModelMapperImpl
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToEntityMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToEntityMapperImpl
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToModelMapperImpl
import br.com.chalenge.rickmorty.data.mapper.CharacterResultResponseToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResultResponseToModelMapperImpl
import br.com.chalenge.rickmorty.data.remote.MoshiFactory
import br.com.chalenge.rickmorty.data.remote.OkHttpClientFactory
import br.com.chalenge.rickmorty.data.remote.Retrofit
import br.com.chalenge.rickmorty.data.remote.RickMortyApi
import br.com.chalenge.rickmorty.data.repository.CharacterRepositoryImpl
import br.com.chalenge.rickmorty.data.repository.paging.PagingMediator
import br.com.chalenge.rickmorty.database.AppDataBase
import br.com.chalenge.rickmorty.database.AppDataBaseFactory
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import br.com.chalenge.rickmorty.doman.usecase.GetCharacterUseCaseImpl
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import br.com.chalenge.rickmorty.ui.character.CharacterViewModel
import br.com.chalenge.rickmorty.ui.character.mapper.CharaCharacterModelToUiModelMapperImpl
import br.com.chalenge.rickmorty.ui.character.mapper.CharacterModelToUiModelMapper
import br.com.chalenge.rickmorty.utils.ResourceManager
import br.com.chalenge.rickmorty.utils.ResourceManagerImpl
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<AppDataBase> {
        AppDataBaseFactory.provideDataBase(context = androidApplication()).build()
    }
    single<Moshi> { MoshiFactory.build() }
    single<OkHttpClient> { OkHttpClientFactory.provideOkHttpClient() }
    single<RickMortyApi> {
        Retrofit.provideRetrofit(
            baseUrl = getProperty(Properties.BASE_URL),
            moshi = get(),
            client = get()
        ).create(RickMortyApi::class.java)
    }
    factory<CharacterResponseToEntityMapper> { CharacterResponseToEntityMapperImpl() }
    factory<CharacterEntityToModelMapper> { CharacterEntityToModelMapperImpl() }
    factory<CharacterResponseToModelMapper> { CharacterResponseToModelMapperImpl() }
    factory<CharacterResultResponseToModelMapper> {
        CharacterResultResponseToModelMapperImpl(characterResponseToModelMapper = get())
    }
    factory { PagingMediator(appDataBase = get(), rickMortyApi = get()) }
    factory<CharacterRepository> {
        CharacterRepositoryImpl(
            appDataBase = get(),
            rickMortyApi = get(),
            characterEntityToModelMapper = get(),
            characterResultResponseToModelMapper = get()
        )
    }
}

val domainModule = module {
    factory<GetCharactersUseCase> { GetCharacterUseCaseImpl(characterRepository = get()) }
}

val uiModule = module {
    factory<ResourceManager> { ResourceManagerImpl(context = androidContext()) }
    factory<CharacterModelToUiModelMapper> {
        CharaCharacterModelToUiModelMapperImpl(resourceManager = get())
    }
    viewModel {
        CharacterViewModel(
            getCharactersUseCase = get(),
            characterModelToUiModelMapper = get()
        )
    }
}