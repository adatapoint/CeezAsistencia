package model.dominio

import javax.inject.Inject
import model.MegaTrait
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class RangoEdadRow(
    id: Option[Long],
    descripcion: String,
    edadInicio: Option[Int],
    edadFin: Option[Int]
)

trait RangoEdadComponent {
  // Definition of the RANGOEDAD table
  class RangoEdadTable(tag: Tag)
      extends Table[RangoEdadRow](tag, "RANGO_EDAD") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def descripcion = column[String]("DESCRIPCION")
    def edadInicio = column[Option[Int]]("EDAD_INICIO")
    def edadFin = column[Option[Int]]("EDAD_FIN")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id.?,
        descripcion,
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
