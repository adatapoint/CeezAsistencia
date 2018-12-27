package model.dominio

import javax.inject.Inject
import model.MegaTrait
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class EstadoProyectoRow(
    id: Long,
    descripcion: String
)

trait EstadoProyectoComponent {
  // Definition of the ESTADO_PROYECTO table
  class EstadoProyectoTable(tag: Tag)
      extends Table[EstadoProyectoRow](tag, "ESTADO_PROYECTO") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def descripcion = column[String]("DESCRIPCION")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        descripcion
      ) <> (EstadoProyectoRow.tupled, EstadoProyectoRow.unapply)
  }
  lazy val estadosProyecto = TableQuery[EstadoProyectoTable]
}

class EstadoProyectoDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getDepartamentoById(id: Long): Future[Option[EstadoProyectoRow]] = {
    val q = estadosProyecto.filter(_.id === id)
    db.run(q.result.headOption)
  }
}

//http://slick.lightbend.com/doc/3.2.3/gettingstarted.html#schema
