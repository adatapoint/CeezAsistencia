package model

import slick.jdbc.SQLiteProfile.api._

case class AsistenciaRow(
                       usuarioId: Long,
                       sesionId: Long
                     )

trait AsistenciaComponent{
  self: UsuarioComponent with SesionComponent =>
  // Definition of the ASISTENCIA table
  class AsistenciaTable(tag: Tag) extends Table[AsistenciaRow] (tag, "ASISTENCIA") {
    def usuarioId  = column[Long]("USUARIO_ID")
    def sesionId = column[Long]("SESION_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (usuarioId, sesionId)<>(AsistenciaRow.tupled, AsistenciaRow.unapply)
    def pk = primaryKey("PK_ASISTENCIA", (usuarioId, sesionId))
    // Tiene FK
    def usuario = foreignKey("USUARIO_FK", usuarioId, usuarios)(_.id)
    def sesion = foreignKey("SESION_FK", sesionId, sesiones)(_.id)
  }
  val asistencias = TableQuery[AsistenciaTable]
}