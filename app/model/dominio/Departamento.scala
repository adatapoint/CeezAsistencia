package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class DepartamentoRow(id: Long, nombre: String)

trait DepartamentoComponent{
  // Definition of the MUNICIPIO table
  class DepartamentoTable(tag: Tag) extends Table[BarrioRow] (tag, "DEPARTAMENTO") {
    def id = column[Long] ("ID", O.PrimaryKey) //Departamento ID doesn't autoinc
    def nombre  = column[String]("NOMBRE")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, nombre)<>(BarrioRow.tupled, BarrioRow.unapply _)
  }
  val departamentos = TableQuery[DepartamentoTable]
}