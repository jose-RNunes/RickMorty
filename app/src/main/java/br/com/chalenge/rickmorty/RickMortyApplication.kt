package br.com.chalenge.rickmorty

import android.app.Application
import br.com.chalenge.rickmorty.di.dataModule
import br.com.chalenge.rickmorty.di.domainModule
import br.com.chalenge.rickmorty.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RickMortyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RickMortyApplication)
            androidLogger(Level.DEBUG)
            androidFileProperties()
            modules(listOf(dataModule, domainModule, uiModule))
        }
    }
}