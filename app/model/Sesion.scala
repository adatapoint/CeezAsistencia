package model

import java.sql.Timestamp

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class SesionRow(
    id: Long,
    nombre: String,
    fecha: Timestamp,
    lugar: String,
    observacion: String,
    cicloId: Long
)

trait SesionComponent {
  self: CicloComponent =>
  // Definition of the SESION table
  class SesionTable(tag: Tag) extends Table[SesionRow](tag, "SESION") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def nombre = column[String]("NOMBRE")
    def fecha = column[Timestamp]("FECHA")
    def lugar = column[String]("LUGAR")
    def observacion = column[String]("OBSERVACION")
    def cicloId = column[Long]("CICLO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        nombre,
        fecha,
        lugar,
        observacion,
        cicloId
      ) <> (SesionRow.tupled, SesionRow.unapply)
    // Tiene FK
    def ciclo = foreignKey("CICLO_FK", cicloId, ciclos)(_.id)
  }
  lazy val sesiones = TableQuery[SesionTable]
}

class SesionDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getSesionById(id: Long): Future[Option[SesionRow]] = {
    val q = sesiones.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
