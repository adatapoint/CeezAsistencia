package model

import slick.jdbc.SQLiteProfile.api._

case class AsistenciaRow(
                       usuarioID: Long,
                       sesionID: Long
                     )

trait AsistenciaComponent{
  self: AsistenciaComponent with UsuarioComponent with SesionComponent =>
  // Definition of the ASISTENCIA table
  class AsistenciaTable(tag: Tag) extends Table[AsistenciaRow] (tag, "ASISTENCIA") {
    def usuarioID  = column[Long]("USUARIO_ID")
    def sesionID = column[Long]("SESION_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (usuarioID, sesionID)<>(AsistenciaRow.tupled, AsistenciaRow.unapply _)
    // Tiene FK
    def usuario = foreignKey("USUARIO_FK", usuarioID, usuarios)(_.id)
    def sesion = foreignKey("SESION_FK", sesionID, sesiones)(_.id)
  }
  val asistencias = TableQuery[AsistenciaTable]
}