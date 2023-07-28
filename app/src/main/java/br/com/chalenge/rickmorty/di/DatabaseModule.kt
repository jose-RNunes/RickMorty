package br.com.chalenge.rickmorty.di

import android.content.Context
import androidx.room.Room
import br.com.chalenge.rickmorty.database.AppDataBase
import br.com.chalenge.rickmorty.database.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            AppDataBase.DB_NAME
        ).build()
    }

    @Provides
    fun providesCharacterDao(appDataBase: AppDataBase): CharacterDao {
        return appDataBase.characterDao()
    }
}