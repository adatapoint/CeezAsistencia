package model.dominio

import javax.inject.Inject
import model.MegaTrait
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class DifusionRow(
    id: Long,
    descripcion: String
)

trait DifusionComponent {
  // Definition of the DIFUSION table
  class DifusionTable(tag: Tag) extends Table[DifusionRow](tag, "DIFUSION") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def descripcion = column[String]("DESCRIPCION")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        descripcion
      ) <> (DifusionRow.tupled, DifusionRow.unapply)
  }
  lazy val difusiones = TableQuery[DifusionTable]
}

class DifusionDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getDifusionById(id: Long): Future[Option[DifusionRow]] = {
    val q = difusiones.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
