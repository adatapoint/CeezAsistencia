package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class DifusionRow(id: Long, descripcion: String)

trait DifusionComponent{
  // Definition of the DIFUSION table
  class DifusionTable(tag: Tag) extends Table[DifusionRow] (tag, "DIFUSION") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def descripcion  = column[String]("DESCRIPCION")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, descripcion)<>(DifusionRow.tupled, DifusionRow.unapply _)
  }
  val difusiones = TableQuery[DifusionTable]
}