package nl.teutonicjoe.api

import zio.ZIO

object AppService {

  def getDogs(page: Int): ZIO[AnimalRepository, Throwable, Animals] =
    ZIO.serviceWithZIO[AnimalRepository](_.getDogs(page))
  def getCats(page: Int): ZIO[AnimalRepository, Throwable, Animals] =
    ZIO.serviceWithZIO[AnimalRepository](_.getCats(page))
}
