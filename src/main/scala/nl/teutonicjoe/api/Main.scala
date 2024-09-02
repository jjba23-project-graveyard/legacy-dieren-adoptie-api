package nl.teutonicjoe.api

import nl.teutonicjoe.api.api.AnimalApp
import zio._
import zio.http.Server

object Main extends ZIOAppDefault {

  override def run: ZIO[Any, Throwable, Nothing] =
    Server.serve(AnimalApp.app).provide(
      //AppConfig.Api.live,
      //      Layers.healthCheckService,
      //      Layers.logger,
      Layers.animalRepository,
      Server.defaultWithPort(8080)
    )
}
