package model

import java.sql.Timestamp

import model.dominio.EstadoProyectoComponent
import slick.jdbc.SQLiteProfile.api._

case class ProyectoRow(
                        id: Long,
                        fechaInicio: Timestamp,
                        fechaFin: Timestamp,
                        descripcion: String,
                        estadoProyectoId: Long
                      )

trait ProyectoComponent{
  self: EstadoProyectoComponent =>
  // Definition of the PROYECTO table
  class ProyectoTable(tag: Tag) extends Table[ProyectoRow] (tag, "PROYECTO") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def fechaInicio = column[Timestamp]("FECHA_INCIO")
    def fechaFin = column[Timestamp]("FECHA_FIN")
    def descripcion = column[String]("DESCRIPCION")
    def estadoProyectoId = column[Long]("PROYECTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (
      id,
      fechaInicio,
      fechaFin,
      descripcion,
      estadoProyectoId
    )<>(ProyectoRow.tupled, ProyectoRow.unapply)
    // Tiene FK
    def estadoProyecto = foreignKey("ESTADO_PROYECTO_FK", estadoProyectoId, estadosProyecto)(_.id)
  }
  val proyectos = TableQuery[ProyectoTable]
}