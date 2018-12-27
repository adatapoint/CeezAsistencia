package model

import java.sql.Timestamp

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class CicloRow(
    id: Option[Long],
    fechaInicio: Timestamp,
    fechaFin: Option[Timestamp],
    descripcion: String,
    proyectoId: Long
)

trait CicloComponent {
  self: ProyectoComponent =>
  // Definition of the CICLO table
  class CicloTable(tag: Tag) extends Table[CicloRow](tag, "CICLO") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def fechaInicio = column[Timestamp]("FECHA_INCIO")
    def fechaFin = column[Timestamp]("FECHA_FIN")
    def descripcion = column[String]("DESCRIPCION")
    def proyectoId = column[Long]("PROYECTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id.?,
        fechaInicio,
        fechaFin.?,
        descripcion,
        proyectoId
      ) <> (CicloRow.tupled, CicloRow.unapply)
    // Tiene FK
    def proyecto = foreignKey("PROYECTO_FK", proyectoId, proyectos)(_.id)
  }
  lazy val ciclos = TableQuery[CicloTable]
}

class CicloDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getCicloById(id: Long): Future[Option[CicloRow]] = {
    val q = ciclos.filter(_.id === id)
    //    val r: Future[Option[UsuarioRow]] = db.run(q.result.headOption) // Retorna un futuro algo (puede ser nulo, entonces ponemos Option) Y como queremos el primero, ponemos head, pero como posible que sea nul, ponemos headOption
    db.run(q.result.headOption)
  }
}
