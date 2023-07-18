package br.com.chalenge.rickmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.chalenge.rickmorty.data.entity.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    internal companion object {
        const val DB_NAME: String = "RickMortyDB"
    }
}