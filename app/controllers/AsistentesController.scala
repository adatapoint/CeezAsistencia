package controllers

import javax.inject._
import model.{MegaTrait, AsistenteDao, AsistenteRow}
import play.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.mvc._
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class AsistentesController @Inject()(cc: ControllerComponents,
                                     asistenteDao: AsistenteDao)
    extends AbstractController(cc) {

//  /**
//    * Create an Action to render an HTML page.
//    *
//    * The configuration in the `routes` file means that this method
//    * will be called when the application receives a `GET` request with
//    * a path of `/`.
//    */
//  def index(): Action[AnyContent] = Action {
//    implicit request: Request[AnyContent] =>
//      Ok(views.html.index())
//  }

//  def suma(num1: Int, num2: Int): Action[AnyContent] = Action {
//    implicit request =>
//      Ok(s"${num1 + num2 * 3}")
//  }

  import scala.concurrent.ExecutionContext.Implicits.global
  def getAsistentesBySesion(sesionId: Long): Action[AnyContent] = Action.async {
    asistenteDao
      .getAsistentesBySesion(sesionId)
      .map(u => Ok(u.toString()))
  }

  implicit val fmtAsistente
    : OFormat[AsistenteRow] = Json.format[AsistenteRow] // Json.writes Json.reads para escritura y lectura. Json.format hace las dos cosas.

  def addAsistente: Action[JsValue] = Action.async(parse.tolerantJson) {
    implicit request => // En el paréntesis recibo los parámetros que llegan en la URL  // Con el parse.json valido que sea un json.
      request.body
        .validate[AsistenteRow]
        .fold(
          error =>
            Future.successful(BadRequest(s"Error: $error")), //Error - Invalid
          asistente => { // Valid
            asistenteDao
              .add(asistente)
              .map(u => Ok(Json.toJson(u)))
              .recover {
                case ex => InternalServerError(s"Error: $ex")
              }
          }
        )
  }

  def updateAsistente: Action[JsValue] = Action.async(parse.tolerantJson) {
    implicit request =>
      request.body
        .validate[AsistenteRow]
        .fold(
          error => Future.successful(BadRequest(s"Error: $error")),
          asistente => {
            asistenteDao
              .update(asistente)
              .map(bool => Ok(Json.toJson(bool)))
              .recover {
                case ex => InternalServerError(s"Error: $ex")
              }
          }
        )
  }

}
