package br.com.chalenge.rickmorty.utils

interface Mapper<T, Z> {

    fun converter(from: T): Z
}