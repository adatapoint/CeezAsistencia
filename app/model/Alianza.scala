package model

import java.sql.Timestamp

import slick.jdbc.SQLiteProfile.api._

case class AlianzaRow(
                       id: Long,
                       fechaInicio: Timestamp,
                       fechaFin: Timestamp,
                       descripcion: String,
                       entidadId: Long,
                       proyectoId: Long
                     )

trait AlianzaComponent{
  self: EntidadComponent with ProyectoComponent =>
  // Definition of the ALIANZA table
  class AlianzaTable(tag: Tag) extends Table[AlianzaRow] (tag, "ALIANZA") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def fechaInicio = column[Timestamp]("FECHA_INCIO")
    def fechaFin = column[Timestamp]("FECHA_FIN")
    def descripcion = column[String]("DESCRIPCION")
    def entidadId  = column[Long]("ENTIDAD_ID")
    def proyectoId = column[Long]("PROYECTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, fechaInicio, fechaFin, descripcion, entidadId, proyectoId)<>(AlianzaRow.tupled, AlianzaRow.unapply)
    // Tiene FK
    def entidad = foreignKey("ENTIDAD_FK", entidadId, entidades)(_.id)
    def proyecto = foreignKey("PROYECTO_FK", proyectoId, proyectos)(_.id)
  }
  val alianzas = TableQuery[AlianzaTable]
}