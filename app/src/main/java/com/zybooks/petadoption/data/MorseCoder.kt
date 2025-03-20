package com.zybooks.petadoption.data

data class Level (
   val id: Int = 0,
   val title: String = "",
   val chars: List<Char> = listOf(),
   val imageIds: List<Int> = listOf()
)