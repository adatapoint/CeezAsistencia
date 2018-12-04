package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class EstadoProyectoRow(id: Long, descripcion: String)

trait EstadoProyectoComponent{
  // Definition of the ESTADO_PROYECTO table
  class EstadoProyectoTable(tag: Tag) extends Table[EstadoProyectoRow](tag, "ESTADO_PROYECTO") {
    def id = column[Long]("ID", O.PrimaryKey)
    def descripcion = column[String]("DESCRIPCION")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, descripcion)<>(EstadoProyectoRow.tupled, EstadoProyectoRow.unapply _)
  }
  val estadosProyecto = TableQuery[EstadoProyectoTable]
}

//http://slick.lightbend.com/doc/3.2.3/gettingstarted.html#schema


