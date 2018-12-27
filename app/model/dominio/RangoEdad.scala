package model.dominio

import javax.inject.Inject
import model.{MegaTrait, ProyectoRow}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class RangoEdadRow(
    id: Long,
    descripcion: String,
    edadInicio: Int,
    edadFin: Int
)

trait RangoEdadComponent {
  // Definition of the RANGOEDAD table
  class RangoEdadTable(tag: Tag)
      extends Table[RangoEdadRow](tag, "RANGO_EDAD") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def nombre = column[String]("NOMBRE")
    def edadInicio = column[Int]("EDAD_INICIO")
    def edadFin = column[Int]("EDAD_FIN")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        nombre,
        edadInicio,
        edadFin
      ) <> (RangoEdadRow.tupled, RangoEdadRow.unapply)
  }
  lazy val rangosEdad = TableQuery[RangoEdadTable]

  class RangoEdadDao @Inject()(
      protected val dbConfigProvider: DatabaseConfigProvider)(
      implicit ex: ExecutionContext)
      extends HasDatabaseConfigProvider[JdbcProfile]
      with MegaTrait {

    def getRangoEdadById(id: Long): Future[Option[RangoEdadRow]] = {
      val q = rangosEdad.filter(_.id === id)
      db.run(q.result.headOption)
    }
  }
}
