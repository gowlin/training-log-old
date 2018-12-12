package query

import javax.inject.Inject
import play.api.libs.json.JsValue
import sangria.execution.Executor
import sangria.macros.derive._
import sangria.marshalling.playJson._
import sangria.parser.QueryParser
import sangria.schema._

import scala.concurrent.{ExecutionContext, Future}

case class Name(id: String, name: String)

class QueryService @Inject()(queryContext: QueryContext) {

  private val PersonsType = deriveObjectType[QueryContext, Name](
    ObjectTypeName("Name"),
    ObjectTypeDescription("A name object")
  )

  private val ID = Argument("id", StringType, description = "Id Argument")

  private val allPersons =
    Seq(Name("ryan", "Ryan Golbeck"), Name("morgan", "Morgan Golbeck"))

  private val Query: ObjectType[QueryContext, Unit] =
    ObjectType[QueryContext, Unit](
      "Query",
      fields[QueryContext, Unit](
        Field("persons",
              PersonsType,
              arguments = ID :: Nil,
              resolve = ctx =>
                allPersons
                  .find(p => p.id == ctx.arg(ID))
                  .getOrElse(Name("unknown", "Unknown")))
      )
    )

  private val RootQuery = Schema(Query)

  def execute(query: String, variables: JsValue)(
      implicit ec: ExecutionContext): Future[JsValue] = {
    for {
      parsed <- Future.fromTry(QueryParser.parse(query))
      result <- Executor.execute(RootQuery,
                                 parsed,
                                 queryContext,
                                 variables = variables)
    } yield { result }
  }
}
