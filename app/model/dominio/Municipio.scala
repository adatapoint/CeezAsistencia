package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class MunicipioRow(id: Long, nombre: String)

trait MunicipioComponent{
  // Definition of the MUNICIPIO table
  class MunicipioTable(tag: Tag) extends Table[MunicipioRow] (tag, "MUNICIPIO") {
    def id = column[Long] ("ID", O.PrimaryKey) //Municipio ID doesn't autoinc
    def nombre  = column[String]("NOMBRE")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, nombre)<>(MunicipioRow.tupled, MunicipioRow.unapply _)
  }
  val municipios = TableQuery[MunicipioTable]
}