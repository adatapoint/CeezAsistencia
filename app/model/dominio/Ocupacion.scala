package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class OcupacionRow(
                         id: Long,
                         nombre: String
                       )

trait OcupacionComponent{
  // Definition of the OCUPACION table
  class OcupacionTable(tag: Tag) extends Table[OcupacionRow](tag, "OCUPACION") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def nombre = column[String]("NOMBRE")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (
      id,
      nombre
    )<>(OcupacionRow.tupled, OcupacionRow.unapply)
  }
  lazy val ocupaciones = TableQuery[OcupacionTable]
}