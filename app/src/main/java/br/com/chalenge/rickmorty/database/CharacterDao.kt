package br.com.chalenge.rickmorty.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.chalenge.rickmorty.data.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters WHERE :name IS NULL OR name LIKE '%' || :name || '%'")
    suspend fun getCharacters(name: String?): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id ")
    suspend fun getCharacter(id: Int): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacters(characters: List<CharacterEntity>)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}