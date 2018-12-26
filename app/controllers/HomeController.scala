package controllers

import javax.inject._
import model.{MegaTrait, UsuarioDao}
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
class HomeController @Inject()(cc: ControllerComponents, usuarioDao: UsuarioDao)
    extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def suma(num1: Int, num2: Int) = Action { implicit request =>
    Ok(s"${num1 + num2 * 3}")
  }

  import scala.concurrent.ExecutionContext.Implicits.global
  def getUsuariosBySesion(sesionId: Long) = Action.async {
    usuarioDao.getUsuariosBySesion(1)
      .map(u => Ok(u.toString()))
  }

}

class Application @Inject()(
    environment: Environment,
    protected val dbConfigProvider: DatabaseConfigProvider,
    cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with HasDatabaseConfigProvider[JdbcProfile] {

  def index = Action.async { implicit request => // Debe ser async, dado que está devovliendo un futuro.
    if (environment.mode == play.api.Mode.Dev) {
      db.run(MegaTrait.getCreateSchema)
        .map(_ => Ok("Creadas las tablas de las bases de datos!"))
    } else {
//      Future(BadRequest) // Tengo que devolver un futuro
      Future.successful(BadRequest)
    }
  }

}
