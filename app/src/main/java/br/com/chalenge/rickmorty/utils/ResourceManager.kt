package br.com.chalenge.rickmorty.utils

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

interface ResourceManager {

    fun getString(@StringRes stringRes: Int): String
}

class ResourceManagerImpl @Inject constructor(private val context: Context): ResourceManager {
    override fun getString(stringRes: Int): String {
        return context.getString(stringRes)
    }
}