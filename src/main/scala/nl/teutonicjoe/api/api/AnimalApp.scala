package nl.teutonicjoe.api.api

import nl.teutonicjoe.api.api.Extensions.RichDomain
import nl.teutonicjoe.api.{AnimalRepository, AppService}
import zio.http.HttpAppMiddleware.cors
import zio.http._
import zio.http.middleware.Cors.CorsConfig
import zio.http.model.Method

import scala.language.postfixOps
import scala.util.Try

object AnimalApp {

  private def toOptInt(s: String) = Try(s.toInt) toOption

  private def parsePageNumFromReq(req: Request): Int = {
    val raw = req.url.queryParams.get("page")

    if (raw.isDefined) {
      val maybeNum = toOptInt(raw.get.asString)
      if (maybeNum.isDefined) {
        maybeNum.get
      } else {
        1
      }
    } else {
      1
    }
  }

  private val corsConfig: CorsConfig =
    CorsConfig(
      allowedOrigins = _ != "",
      allowedMethods = Some(Set(Method.GET, Method.POST, Method.PATCH, Method.PUT, Method.DELETE))
    )

  val app: Http[AnimalRepository, Nothing, Request, Response] = Http.collectZIO[Request] {
    case req@Method.GET -> _ / "dogs" =>
      AppService
        .getDogs(parsePageNumFromReq(req))
        .foldZIO(Utils.handleError, _.toResponseZIO)

    case req@Method.GET -> _ / "cats" =>
      AppService
        .getCats(parsePageNumFromReq(req))
        .foldZIO(Utils.handleError, _.toResponseZIO)
  } @@ cors(corsConfig)
}
