package query

import javax.inject.Inject
import org.mongodb.scala._
import play.api.Configuration
import query.Helpers._

class QueryContext @Inject()(configuration: Configuration) {
  private val mongoClient: MongoClient = MongoClient(
    configuration.get[String]("training-log.mongo-url"))
  private val trainingDb: MongoDatabase = mongoClient.getDatabase("training_db")

  val collection = trainingDb.getCollection("workouts")

  println("Number of docus")
  collection.countDocuments().printHeadResult()
}
