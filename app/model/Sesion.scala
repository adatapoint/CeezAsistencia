package model

import java.sql.Timestamp

import slick.jdbc.SQLiteProfile.api._

case class SesionRow(
                    id: Long,
                    nombre: String,
                    fecha: Timestamp,
                    lugar: String,
                    observacion: String,
                    cicloId: Long
                    )

trait SesionComponent{
  self: CicloComponent =>
  // Definition of the SESION table
  class SesionTable(tag: Tag) extends Table[SesionRow] (tag, "SESION") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def nombre = column[String]("NOMBRE")
    def fecha = column[Timestamp]("FECHA")
    def lugar = column[String]("LUGAR")
    def observacion = column[String]("OBSERVACION")
    def cicloId = column[Long]("CICLO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (
      id,
      nombre,
      fecha,
      lugar,
      observacion,
      cicloId
    )<>(SesionRow.tupled, SesionRow.unapply)
    // Tiene FK
    def ciclo = foreignKey("CICLO_FK", cicloId, ciclos)(_.id)
  }
  val sesiones = TableQuery[SesionTable]
}