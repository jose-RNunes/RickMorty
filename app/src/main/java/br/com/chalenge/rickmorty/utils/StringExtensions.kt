package br.com.chalenge.rickmorty.utils

fun String.capitalizeText(): String {
    return this.lowercase().replaceFirstChar { char -> char.uppercase() }
}