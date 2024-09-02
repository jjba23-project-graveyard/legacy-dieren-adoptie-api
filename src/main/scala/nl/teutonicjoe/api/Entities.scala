package nl.teutonicjoe.api

import zio.json._

sealed trait AnimalSpecies

case object DogSpecies extends AnimalSpecies

case object CatSpecies extends AnimalSpecies

case class BasicAnimalAttributes(
  name: String,
  species: Option[String],
  breed: Option[String],
  description: Option[String],
  birthday: Option[String],
  gender: Option[String],
  neutered: Option[String],
  height: Option[String],
  createdAt: Long,
  updatedAt: Option[Long]
)

object BasicAnimalAttributes {
  implicit val encoder: JsonEncoder[BasicAnimalAttributes] =
    DeriveJsonEncoder.gen[BasicAnimalAttributes]
  implicit val decoder: JsonDecoder[BasicAnimalAttributes] =
    DeriveJsonDecoder.gen[BasicAnimalAttributes]
}

case class ShelterAttributes(
  name: Option[String],
  address: Option[String],
  animalTimeInShelter: Option[String]
)

object ShelterAttributes {
  implicit val encoder: JsonEncoder[ShelterAttributes] =
    DeriveJsonEncoder.gen[ShelterAttributes]
  implicit val decoder: JsonDecoder[ShelterAttributes] =
    DeriveJsonDecoder.gen[ShelterAttributes]
}

case class BehaviouralAttributes(
  behaviourWithCats: Option[String],
  behaviourWithDogs: Option[String],
  behaviourWithChildren: Option[String],
  canStayHomeAlone: Option[String],
  canTravelInCar: Option[String],
  specialNeeds: Option[String],
  leashedBehaviour: Option[String],
  needsGoingOutside: Option[String],
  enjoysHumanAffection: Option[String]
)

object BehaviouralAttributes {
  implicit val encoder: JsonEncoder[BehaviouralAttributes] =
    DeriveJsonEncoder.gen[BehaviouralAttributes]
  implicit val decoder: JsonDecoder[BehaviouralAttributes] =
    DeriveJsonDecoder.gen[BehaviouralAttributes]
}

case class AnimalSkills(
  basicCommandsMastered: Option[String],
  pottyTrained: Option[String],
  vigilance: Option[String],
  unleashedWalkPossible: Option[String]
)

object AnimalSkills {
  implicit val encoder: JsonEncoder[AnimalSkills] =
    DeriveJsonEncoder.gen[AnimalSkills]
  implicit val decoder: JsonDecoder[AnimalSkills] =
    DeriveJsonDecoder.gen[AnimalSkills]
}

case class Animals(items: Seq[Animal])

object Animals {
  implicit val encoder: JsonEncoder[Animals] =
    DeriveJsonEncoder.gen[Animals]
  implicit val decoder: JsonDecoder[Animals] =
    DeriveJsonDecoder.gen[Animals]
}

case class Animal(
  uniqueId: String,
  images: Iterable[String],
  basicAnimalAttributes: BasicAnimalAttributes,
  shelterAttributes: ShelterAttributes,
  behaviouralAttributes: BehaviouralAttributes,
  skills: AnimalSkills,
  hairCareNeeded: Option[String],
  availability: Option[String],
  originalSource: Option[String],
  isFarmCat: Option[String]
)

object Animal {
  implicit val encoder: JsonEncoder[Animal] =
    DeriveJsonEncoder.gen[Animal]
  implicit val decoder: JsonDecoder[Animal] =
    DeriveJsonDecoder.gen[Animal]
}
