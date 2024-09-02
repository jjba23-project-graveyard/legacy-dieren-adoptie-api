package nl.teutonicjoe.api

import com.mongodb.MongoClientSettings
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Sorts._
import zio._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


trait AnimalRepository {

  def getCats(page: Int): ZIO[Any, Throwable, Animals]

  def getDogs(page: Int): ZIO[Any, Throwable, Animals]
}

case class MongoAnimalRepository() extends AnimalRepository {

  private val mongoDBName = "dieren-adoptie"
  private val mongoUserName = "dieren-adoptie-user"
  private val mongoPassword = "dieren-adoptie-password"
  private val perPage = 15

  private val mongoURI =
    s"mongodb://${mongoUserName}:${mongoPassword}@localhost:27117/${mongoDBName}"

  private val myCodecRegistry = fromRegistries(
    fromProviders(
      createCodecProviderIgnoreNone[BasicAnimalAttributes](),
      createCodecProviderIgnoreNone[ShelterAttributes](),
      createCodecProviderIgnoreNone[BehaviouralAttributes](),
      createCodecProviderIgnoreNone[AnimalSkills](),
      createCodecProviderIgnoreNone[Animal]()
    ),
    DEFAULT_CODEC_REGISTRY
  )

  private val mongoClientSettings = MongoClientSettings
    .builder()
    .codecRegistry(myCodecRegistry)
    .applyConnectionString(ConnectionString(mongoURI))
    .retryWrites(false)
    .build()
  private val mongoClient = MongoClient(mongoClientSettings)
  private val db =
    mongoClient.getDatabase(mongoDBName).withCodecRegistry(myCodecRegistry)

  private val dogCollection: MongoCollection[Animal] =
    db.getCollection("Dog")

  private val catCollection: MongoCollection[Animal] =
    db.getCollection("Cat")

  private val readOperationTimeout = Duration.Inf

  override def getDogs(page: Int): ZIO[Any, Throwable, Animals] = {
    val f = dogCollection
      .find()
      .sort(ascending("basicAnimalAttributes.createdAt"))
      .skip(page * perPage)
      .limit(perPage)
      .allowDiskUse(true)
      .toFuture()
    val r = Await.result(f, readOperationTimeout)
    ZIO.succeed(Animals(items = r.toList))
  }

  override def getCats(page: Int): ZIO[Any, Throwable, Animals] = {
    val f = catCollection
      .find()
      .sort(ascending("basicAnimalAttributes.createdAt"))
      .skip(page * perPage)
      .limit(perPage)
      .allowDiskUse(true)
      .toFuture()
    val r = Await.result(f, readOperationTimeout)
    ZIO.succeed(Animals(items = r.toList))
  }
}

object MongoAnimalRepository {
  val live: ULayer[MongoAnimalRepository] = ZLayer.succeed(MongoAnimalRepository())
}
