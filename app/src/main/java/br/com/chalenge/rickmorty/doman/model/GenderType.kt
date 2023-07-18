package br.com.chalenge.rickmorty.doman.model

enum class GenderType {
    FEMALE,
    MALE,
    GENDERLESS,
    UNKNOWN;

    companion object {
        fun find(gender: String): GenderType {
            return values().firstOrNull { genderType ->
                genderType.name == gender.uppercase()
            } ?: UNKNOWN
        }
    }
}