package model

import java.sql.Timestamp

import slick.jdbc.SQLiteProfile.api._

case class AlianzaRow(
                       id: Long,
                       fechaInicio: Timestamp,
                       fechaFin: Timestamp,
                       descripcion: String,
                       entidadID: Long,
                       proyectoID: Long
                     )

trait AlianzaComponent{
  self: AsistenciaComponent with EntidadComponent with ProyectoComponent =>
  // Definition of the ALIANZA table
  class AlianzaTable(tag: Tag) extends Table[AlianzaRow] (tag, "ALIANZA") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def fechaInicio = column[Timestamp]("FECHA_INCIO")
    def fechaFin = column[Timestamp]("FECHA_FIN")
    def descripcion = column[String]("DESCRIPCION")
    def entidadID  = column[Long]("ENTIDAD_ID")
    def proyectoID = column[Long]("PROYECTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, fechaInicio, fechaFin, descripcion, entidadID, proyectoID)<>(AlianzaRow.tupled, AlianzaRow.unapply)
    // Tiene FK
    def entidad = foreignKey("ENTIDAD_FK", entidadID, entidades)(_.id)
    def proyecto = foreignKey("PROYECTO_FK", proyectoID, proyectos)(_.id)
  }
  val alianzas = TableQuery[AlianzaTable]
}