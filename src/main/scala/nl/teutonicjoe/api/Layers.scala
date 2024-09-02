package nl.teutonicjoe.api

//import nl.teutonicjoe.api.api.healthcheck.{ HealthCheckService, InMemoryHealthCheckService }
import zio._
//import zio.logging.backend.SLF4J

object Layers {

//  val logger: ULayer[Unit] = ZLayer.make[Unit](
//    Runtime.removeDefaultLoggers,
//    SLF4J.slf4j
//  )

//  val healthCheckService: ULayer[HealthCheckService] = InMemoryHealthCheckService.live
  val animalRepository: ULayer[AnimalRepository]     = MongoAnimalRepository.live
}
