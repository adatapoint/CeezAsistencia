package model

import java.sql.Timestamp

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class AlianzaRow(
    id: Option[Long],
    fechaInicio: Timestamp,
    fechaFin: Option[Timestamp],
    descripcion: Option[String],
    entidadId: Long,
    proyectoId: Long
)

trait AlianzaComponent {
  self: EntidadComponent with ProyectoComponent =>
  // Definition of the ALIANZA table
  class AlianzaTable(tag: Tag) extends Table[AlianzaRow](tag, "ALIANZA") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def fechaInicio = column[Timestamp]("FECHA_INCIO")
    def fechaFin = column[Option[Timestamp]]("FECHA_FIN") // Aquí ya se tiene que es opcional pero para la base de datos.
    def descripcion = column[String]("DESCRIPCION")
    def entidadId = column[Long]("ENTIDAD_ID")
    def proyectoId = column[Long]("PROYECTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id.?,
        fechaInicio,
        fechaFin,
        descripcion.?,
        entidadId,
        proyectoId
      ) <> (AlianzaRow.tupled, AlianzaRow.unapply)
    // Tiene FK
    def entidad = foreignKey("ENTIDAD_FK", entidadId, entidades)(_.id)
    def proyecto = foreignKey("PROYECTO_FK", proyectoId, proyectos)(_.id)
  }
  lazy val alianzas = TableQuery[AlianzaTable]
}

class AlianzaDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getAlianzaById(id: Long): Future[Option[AlianzaRow]] = {
    val q = alianzas.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
