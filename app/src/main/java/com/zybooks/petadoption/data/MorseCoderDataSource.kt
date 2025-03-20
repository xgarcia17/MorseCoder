package com.zybooks.petadoption.data

class MorseCoderDataSource {
   private val levelList = listOf(
      Level (
         id = 1,
         title = "Learn the numbers",
         chars = (0..9).map { it.toString().first() }
      ),
      Level (
         id = 2,
         title = "Learn the alphabet",
         chars = ('A'..'Z').map { it }
      )
   )

   // S: short (dot), L: long (dash)
   private val charCodes = mapOf(
      '0' to "LLLLL",
      '1' to "SLLLL",
      '2' to "SSLLL",
      '3' to "SSSLL",
      '4' to "SSSSL",
      '5' to "SSSSS",
      '6' to "LSSSS",
      '7' to "LLSSS",
      '8' to "LLLSS",
      '9' to "LLLLS",
      'A' to "SL",
      'B' to "LSSS",
      'C' to "LSLS",
      'D' to "LSS",
      'E' to "S",
      'F' to "SSLS",
      'G' to "LLS",
      'H' to "SSSS",
      'I' to "SS",
      'J' to "SLLL",
      'K' to "LSL",
      'L' to "SLSS",
      'M' to "LL",
      'N' to "LS", 
      'O' to "LLL",
      'P' to "SLLS",
      'Q' to "LLSL",
      'R' to "SLS",
      'S' to "SSS",
      'T' to "L",
      'U' to "SSL",
      'V' to "SSSL",
      'W' to "SLL",
      'X' to "LSSL",
      'Y' to "LSLL",
      'Z' to "LLSS"
   )

   fun getLevel(id: Int): Level? {
      return levelList.find { it.id == id }
   }

   fun loadLevels() = levelList

   fun getCharCode(id: Char): String? {
      return charCodes[id]
   }
}