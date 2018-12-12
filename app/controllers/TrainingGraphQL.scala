package controllers

import javax.inject.Inject
import play.api.libs.json.{JsDefined, JsObject, JsUndefined, JsValue}
import play.api.mvc._
import query.QueryService

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class TrainingGraphQL @Inject()(
    cc: ControllerComponents,
    queryService: QueryService)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def entry: Action[AnyContent] = Action.async { request =>
    request.body.asJson match {
      case Some(json) =>
        executeQuery(
          (json \ "query").get.as[String],
          (json \ "variables").getOrElse(JsObject.empty).as[JsObject])
      case None => Future(BadRequest("Request body must not be empty"))
    }
  }

  private def executeQuery(query: String,
                           variables: JsObject): Future[Result] = {
    queryService
      .execute(query, variables)
      .map(queryResult =>
        queryResult \ "errors" match {
          case JsUndefined() => Ok(queryResult)
          case JsDefined(_)  => BadRequest(queryResult)
      })
      .recover { case NonFatal(e) => BadRequest(e.getMessage) }
  }
}
