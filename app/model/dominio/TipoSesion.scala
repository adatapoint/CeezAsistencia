package model.dominio

import javax.inject.Inject
import model.{MegaTrait, ProyectoRow}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class TipoSesionRow(
    id: Long,
    descripcion: String
)

trait TipoSesionComponent {
  // Definition of the TIPOSESION table
  class TipoSesionTable(tag: Tag)
      extends Table[TipoSesionRow](tag, "TIPO_SESION") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def descripcion = column[String]("DESCRIPCION")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        descripcion,
      ) <> (TipoSesionRow.tupled, TipoSesionRow.unapply)
  }
  lazy val tiposSesion = TableQuery[TipoSesionTable]

  class TipoSesionDao @Inject()(
      protected val dbConfigProvider: DatabaseConfigProvider)(
      implicit ex: ExecutionContext)
      extends HasDatabaseConfigProvider[JdbcProfile]
      with MegaTrait {

    def getTipoSesionById(id: Long): Future[Option[TipoSesionRow]] = {
      val q = tiposSesion.filter(_.id === id)
      db.run(q.result.headOption)
    }
  }
}
