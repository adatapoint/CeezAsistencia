package controllers

import javax.inject._
import model.{AsistenteDao, MegaTrait}
import play.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc._
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class DataBaseController @Inject()(
    environment: Environment,
    protected val dbConfigProvider: DatabaseConfigProvider,
    cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with HasDatabaseConfigProvider[JdbcProfile] {

  def createDataBase: Action[AnyContent] = Action.async { implicit request => // Debe ser async, dado que está devolviendo un futuro.
    if (environment.mode == play.api.Mode.Dev) {
      db.run(MegaTrait.getCreateSchema)
        .map(_ => Ok("Creadas las tablas de las bases de datos!"))
    } else {
      //  Future(BadRequest) // Tengo que devolver un futuro
      Future.successful(BadRequest)
    }
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

}
