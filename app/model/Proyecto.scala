package model

import java.sql.Timestamp

import javax.inject.Inject
import model.dominio.{DepartamentoRow, EstadoProyectoComponent}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class ProyectoRow(
    id: Long,
    fechaInicio: Timestamp,
    fechaFin: Timestamp,
    descripcion: String,
    estadoProyectoId: Long
)

trait ProyectoComponent {
  self: EstadoProyectoComponent =>
  // Definition of the PROYECTO table
  class ProyectoTable(tag: Tag) extends Table[ProyectoRow](tag, "PROYECTO") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def fechaInicio = column[Timestamp]("FECHA_INCIO")
    def fechaFin = column[Timestamp]("FECHA_FIN")
    def descripcion = column[String]("DESCRIPCION")
    def estadoProyectoId = column[Long]("ESTADO_PROYECTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        fechaInicio,
        fechaFin,
        descripcion,
        estadoProyectoId
      ) <> (ProyectoRow.tupled, ProyectoRow.unapply)
    // Tiene FK
    def estadoProyecto =
      foreignKey("ESTADO_PROYECTO_FK", estadoProyectoId, estadosProyecto)(_.id)
  }
  lazy val proyectos = TableQuery[ProyectoTable]
}

class ProyectoDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getProyectoById(id: Long): Future[Option[ProyectoRow]] = {
    val q = proyectos.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
