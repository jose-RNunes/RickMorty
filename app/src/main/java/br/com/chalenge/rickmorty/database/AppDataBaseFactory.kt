package br.com.chalenge.rickmorty.database

import android.content.Context
import androidx.room.Room

object AppDataBaseFactory {

    fun provideDataBase(context: Context) = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        AppDataBase.DB_NAME
    )
}