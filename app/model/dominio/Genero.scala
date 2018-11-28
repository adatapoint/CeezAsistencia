package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class GeneroRow(id: Long, descripcion: String)

trait GeneroComponent{
  // Definition of the SUPPLIERS table
  class GeneroTable(tag: Tag) extends Table[GeneroRow](tag, "GENERO") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // This is the primary key column
    def descripcion = column[String]("DESCRIPCION")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, descripcion)<>(GeneroRow.tupled, GeneroRow.unapply _)
  }
  val generos = TableQuery[GeneroTable]
}

//http://slick.lightbend.com/doc/3.2.3/gettingstarted.html#schema



