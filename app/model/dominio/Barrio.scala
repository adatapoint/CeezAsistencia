package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class BarrioRow(
                    id: Long,
                    nombre: String,
                    municipioID: Long
                    )

trait BarrioComponent{
  self: BarrioComponent with MunicipioComponent =>
  // Definition of the BARRIO table
  class BarrioTable(tag: Tag) extends Table[BarrioRow] (tag, "BARRIO") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def nombre  = column[String]("NOMBRE")
    def municipioID = column[Long]("MUNICIPIO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, nombre, municipioID)<>(BarrioRow.tupled, BarrioRow.unapply _)
    // Tiene FK
    def municipio = foreignKey("MUNICIPIO_FK", municipioID, municipios)(_.id)
  }
  val barrios = TableQuery[BarrioTable]
}