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

   private val petList = listOf(
      Pet(
         id = 1,
         type = PetType.DOG,
         name = "Trigger",
         gender = PetGender.MALE,
         age = 4,
         description = "Trigger is a smart dog who loves to bark.",
         imageId = R.drawable.pet1
      ),
      Pet(
         id = 2,
         type = PetType.DOG,
         name = "Gus",
         gender = PetGender.MALE,
         age = 4,
         description = "Gus is a fun-loving dog who loves to chase squirrels.",
         imageId = R.drawable.pet2
      ),
      Pet(
         id = 3,
         type = PetType.CAT,
         name = "Rufus",
         gender = PetGender.MALE,
         age = 2,
         description = "Rufus is a fun-loving cat with lots of energy.",
         imageId = R.drawable.pet3
      ),
      Pet(
         id = 4,
         type = PetType.DOG,
         name = "Daisy",
         gender = PetGender.FEMALE,
         age = 4,
         description = "Daisy is in search for her forever home.",
         imageId = R.drawable.pet4
      ),
      Pet(
         id = 5,
         type = PetType.DOG,
         name = "Gus",
         gender = PetGender.MALE,
         age = 5,
         description = "Gus is a fun-loving dog who loves to chase squirrels.",
         imageId = R.drawable.pet5
      )
   )

   fun getPet(id: Int): Pet? {
      return petList.find { it.id == id }
   }

   fun getLevel(id: Int): Level? {
      return levelList.find { it.id == id }
   }

   fun loadLevels() = levelList
}