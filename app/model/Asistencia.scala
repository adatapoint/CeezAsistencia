package model

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class AsistenciaRow(
    usuarioId: Long,
    sesionId: Long
)

trait AsistenciaComponent {
  self: UsuarioComponent with SesionComponent =>
  // Definition of the ASISTENCIA table
  class AsistenciaTable(tag: Tag)
      extends Table[AsistenciaRow](tag, "ASISTENCIA") {
    def usuarioId = column[Long]("USUARIO_ID")
    def sesionId = column[Long]("SESION_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        usuarioId,
        sesionId
      ) <> (AsistenciaRow.tupled, AsistenciaRow.unapply)
    def pk = primaryKey("PK_ASISTENCIA", (usuarioId, sesionId))
    // Tiene FK
    def usuario = foreignKey("USUARIO_FK", usuarioId, usuarios)(_.id)
    def sesion = foreignKey("SESION_FK", sesionId, sesiones)(_.id)
  }
  lazy val asistencias = TableQuery[AsistenciaTable]
}

class AsistenciaDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getAsistenciaByUsuarioAndSesion(
      usuarioId: Long,
      sesionId: Long
  ): Future[Boolean] = {
    val q = asistencias
      .filter(x => x.usuarioId === usuarioId && x.sesionId === sesionId)
      .exists
    db.run(q.result)
  }
}
