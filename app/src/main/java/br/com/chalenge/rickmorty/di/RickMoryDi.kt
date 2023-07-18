package br.com.chalenge.rickmorty.di

import androidx.room.RoomDatabase
import br.com.chalenge.rickmorty.data.mapper.CharacterEntityToModelMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterEntityToModelMapperImpl
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToEntityMapper
import br.com.chalenge.rickmorty.data.mapper.CharacterResponseToEntityMapperImpl
import br.com.chalenge.rickmorty.data.remote.MoshiFactory
import br.com.chalenge.rickmorty.data.remote.OkHttpClientFactory
import br.com.chalenge.rickmorty.data.remote.Retrofit
import br.com.chalenge.rickmorty.data.remote.RickMortyApi
import br.com.chalenge.rickmorty.data.repository.CharacterRepositoryImpl
import br.com.chalenge.rickmorty.data.repository.paging.PagingMediator
import br.com.chalenge.rickmorty.database.AppDataBase
import br.com.chalenge.rickmorty.database.AppDataBaseFactory
import br.com.chalenge.rickmorty.database.CharacterDao
import br.com.chalenge.rickmorty.doman.repository.CharacterRepository
import br.com.chalenge.rickmorty.doman.usecase.GetCharacterUseCaseImpl
import br.com.chalenge.rickmorty.doman.usecase.GetCharactersUseCase
import br.com.chalenge.rickmorty.ui.character.CharacterViewModel
import br.com.chalenge.rickmorty.ui.character.mapper.CharaCharacterModelToUiModelMapperImpl
import br.com.chalenge.rickmorty.ui.character.mapper.CharacterModelToUiModelMapper
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

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
    factory { PagingMediator(appDataBase = get(), rickMortyApi = get()) }
    factory<CharacterRepository> {
        CharacterRepositoryImpl(
            pagingMediator = get(),
            appDataBase = get(),
            characterEntityToModelMapper = get()
        )
    }
}

val domainModule = module {
    factory<GetCharactersUseCase> { GetCharacterUseCaseImpl(characterRepository = get()) }
}

val uiModule = module {
    factory<CharacterModelToUiModelMapper> { CharaCharacterModelToUiModelMapperImpl() }
    viewModel { CharacterViewModel(getCharactersUseCase = get(), characterModelToUiModelMapper = get()) }
}