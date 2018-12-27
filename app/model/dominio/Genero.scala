package model.dominio

import javax.inject.Inject
import model.MegaTrait
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class GeneroRow(
    id: Option[Long],
    descripcion: String
)

trait GeneroComponent {
  // Definition of the GENERO table
  class GeneroTable(tag: Tag) extends Table[GeneroRow](tag, "GENERO") {
    def id =
      column[Long]("ID", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def descripcion = column[String]("DESCRIPCION")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id.?,
        descripcion
      ) <> (GeneroRow.tupled, GeneroRow.unapply)
  }
  lazy val generos = TableQuery[GeneroTable]
}

//http://slick.lightbend.com/doc/3.2.3/gettingstarted.html#schema

class Genero @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getGeneroById(id: Long): Future[Option[GeneroRow]] = {
    val q = generos.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
