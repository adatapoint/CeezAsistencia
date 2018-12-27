package model.dominio

import javax.inject.Inject
import model.{MegaTrait, ProyectoRow}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class OcupacionRow(
    id: Option[Long],
    nombre: String
)

trait OcupacionComponent {
  // Definition of the OCUPACION table
  class OcupacionTable(tag: Tag) extends Table[OcupacionRow](tag, "OCUPACION") {
    def id =
      column[Long]("ID", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def nombre = column[String]("NOMBRE")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id.?,
        nombre
      ) <> (OcupacionRow.tupled, OcupacionRow.unapply)
  }
  lazy val ocupaciones = TableQuery[OcupacionTable]

  class OcupacionDao @Inject()(
      protected val dbConfigProvider: DatabaseConfigProvider)(
      implicit ex: ExecutionContext)
      extends HasDatabaseConfigProvider[JdbcProfile]
      with MegaTrait {

    def getOcupacionById(id: Long): Future[Option[OcupacionRow]] = {
      val q = ocupaciones.filter(_.id === id)
      db.run(q.result.headOption)
    }
  }
}
