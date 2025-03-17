package com.zybooks.petadoption.data

import com.zybooks.petadoption.R

class PetDataSource {
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
      ),
      Pet(
         id = 6,
         type = PetType.DOG,
         name = "Lilly",
         gender = PetGender.FEMALE,
         age = 5,
         description = "Lilly loves being outside.",
         imageId = R.drawable.pet6
      ),
      Pet(
         id = 7,
         type = PetType.DOG,
         name = "Bella",
         gender = PetGender.FEMALE,
         age = 4,
         description = "Bella is a sweetheart who loves long walks.",
         imageId = R.drawable.pet7
      ),
      Pet(
         id = 8,
         type = PetType.CAT,
         name = "Sam",
         gender = PetGender.MALE,
         age = 5,
         description = "Sam loves to sit and watch TV.",
         imageId = R.drawable.pet8
      ),
      Pet(
         id = 9,
         type = PetType.CAT,
         name = "Jessy",
         gender = PetGender.FEMALE,
         age = 2,
         description = "Jessy is a frisky cat with lots of energy.",
         imageId = R.drawable.pet9
      ),
      Pet(
         id = 10,
         type = PetType.CAT,
         name = "Bubba",
         gender = PetGender.MALE,
         age = 5,
         description = "Bubba is just looking for a good time.",
         imageId = R.drawable.pet10
      )
   )

   fun getPet(id: Int): Pet? {
      return petList.find { it.id == id }
   }

   fun loadPets() = petList
}