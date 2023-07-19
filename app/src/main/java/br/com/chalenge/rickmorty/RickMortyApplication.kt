package br.com.chalenge.rickmorty

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RickMortyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
    }
}