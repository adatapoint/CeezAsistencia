package model

import java.sql.Timestamp

import slick.jdbc.SQLiteProfile.api._

case class CicloRow(
                   id: Long,
                   fechaInicio: Timestamp,
                   fechaFin: Timestamp,
                   descripcion: String,
                   proyectoId: Long
                   )

trait CicloComponent{
  self: ProyectoComponent =>
  // Definition of the CICLO table
  class CicloTable(tag: Tag) extends Table[CicloRow] (tag, "CICLO") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def fechaInicio = column[Timestamp]("FECHA_INCIO")
    def fechaFin = column[Timestamp]("FECHA_FIN")
    def descripcion = column[String]("DESCRIPCION")
    def proyectoId = column[Long]("PROYECTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (
      id,
      fechaInicio,
      fechaFin,
      descripcion,
      proyectoId
    )<>(CicloRow.tupled, CicloRow.unapply)
    // Tiene FK
    def proyecto = foreignKey("PROYECTO_FK", proyectoId, proyectos)(_.id)
  }
  lazy val ciclos = TableQuery[CicloTable]
}