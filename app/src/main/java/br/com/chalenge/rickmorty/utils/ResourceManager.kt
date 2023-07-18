package br.com.chalenge.rickmorty.utils

import android.content.Context
import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes stringRes: Int): String
}

class ResourceManagerImpl(private val context: Context): ResourceManager {
    override fun getString(stringRes: Int): String {
        return context.getString(stringRes)
    }
}