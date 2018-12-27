package model.dominio

import javax.inject.Inject
import model.MegaTrait
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class DepartamentoRow(
    id: Long,
    nombre: String
)

trait DepartamentoComponent {
  // Definition of the DEPARTAMENTO table
  class DepartamentoTable(tag: Tag)
      extends Table[DepartamentoRow](tag, "DEPARTAMENTO") {
    def id = column[Long]("ID", O.PrimaryKey) //Departamento ID doesn't autoinc
    def nombre = column[String]("NOMBRE")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        nombre
      ) <> (DepartamentoRow.tupled, DepartamentoRow.unapply)
  }
  lazy val departamentos = TableQuery[DepartamentoTable]
}

class DepartamentoDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getDepartamentoById(id: Long): Future[Option[DepartamentoRow]] = {
    val q = departamentos.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
