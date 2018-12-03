package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class OcupacionRow(id: Long, descripcion: String)

trait OcupacionComponent{
  // Definition of the OCUPACION table
  class OcupacionTable(tag: Tag) extends Table[GeneroRow](tag, "GENERO") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def descripcion = column[String]("DESCRIPCION")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, descripcion)<>(GeneroRow.tupled, GeneroRow.unapply _)
  }
  val ocupaciones = TableQuery[OcupacionTable]
}