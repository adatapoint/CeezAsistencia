package controllers


import javax.inject._
import model.{AsistenciaDao, AsistenciaRow}
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._

import scala.concurrent.Future


/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class AsistenciasController @Inject()(cc: ControllerComponents, asistenciaDao: AsistenciaDao)
    extends AbstractController(cc) {

  import scala.concurrent.ExecutionContext.Implicits.global
  def getAsistenciasBySesion(sesionId: Long): Action[AnyContent] = Action.async {
    asistenciaDao
      .getAsistenciasBySesion(sesionId)
      .map(u => Ok(u.toString()))
  }

  implicit val fmtAsistencia
  : OFormat[AsistenciaRow] = Json.format[AsistenciaRow] // Json.writes Json.reads para escritura y lectura. Json.format hace las dos cosas.


  def addAsistencia: Action[JsValue] = Action.async(parse.tolerantJson) {
    implicit request =>
      request.body
        .validate[AsistenciaRow]
        .fold(
          error =>
            Future.successful(BadRequest(s"Error: $error")),
          asistencia => {
            asistenciaDao
              .add(asistencia)
              .map(u => Ok(Json.toJson(true)))
              .recover{
                case ex => InternalServerError(s"Error: $ex")
              }
          }
        )
  }

}
