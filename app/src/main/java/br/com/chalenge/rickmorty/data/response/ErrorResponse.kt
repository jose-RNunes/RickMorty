package br.com.chalenge.rickmorty.data.response

import java.io.IOException

abstract class DefaultException(open val errorMessage: String): IOException(errorMessage)

data class NetworkException(override val errorMessage: String): DefaultException(errorMessage)

data class UnexpectedErrorResponse(override val errorMessage: String): DefaultException(errorMessage)