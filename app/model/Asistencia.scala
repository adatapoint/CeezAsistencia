package model

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class AsistenciaRow(
    asistenteId: Long,
    sesionId: Long
)

trait AsistenciaComponent {
  self: AsistenteComponent with SesionComponent =>
  // Definition of the ASISTENCIA table
  class AsistenciaTable(tag: Tag)
      extends Table[AsistenciaRow](tag, "ASISTENCIA") {
    def asistenteId = column[Long]("ASISTENTE_ID")
    def sesionId = column[Long]("SESION_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        asistenteId,
        sesionId
      ) <> (AsistenciaRow.tupled, AsistenciaRow.unapply)
    def pk = primaryKey("PK_ASISTENCIA", (asistenteId, sesionId))
    // Tiene FK
    def asistente = foreignKey("ASISTENTE_FK", asistenteId, asistentes)(_.id)
    def sesion = foreignKey("SESION_FK", sesionId, sesiones)(_.id)
  }
  lazy val asistencias = TableQuery[AsistenciaTable]
}

class AsistenciaDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getAsistenciaByAsistenteAndSesion(
      asistenteId: Long,
      sesionId: Long
  ): Future[Boolean] = {
    val q = asistencias
      .filter(x => x.asistenteId === asistenteId && x.sesionId === sesionId)
      .exists
    db.run(q.result)
  }

  def getAsistenciasBySesion(sesionId: Long): Future[Seq[AsistenciaRow]] ={
    val q = asistencias
      .filter(x => x.sesionId === sesionId)
    db.run(q.result)
  }

  def add(asistencia: AsistenciaRow): Future[AsistenciaRow] = {
    val q = asistencias += asistencia
    db.run(q).map(_ => asistencia) // Una vez ejecutada la consulta, el resultado se mapea, y cualquier cosa que me llegue, igual me envía el asistente.
  }
}
