package com.zybooks.petadoption.data

import com.zybooks.petadoption.R

class MorseCoderDataSource {
   private val levelList = listOf(
      Level (
         id = 1,
         title = "Learn the numbers",
         chars = (1..10).map { it.toString().first() }
      ),
      Level (
         id = 2,
         title = "Learn the alphabet",
         chars = ('a'..'z').map { it }
      )
   )

   fun getLevel(id: Int): Level? {
      return levelList.find { it.id == id }
   }

   fun loadLevels() = levelList
}