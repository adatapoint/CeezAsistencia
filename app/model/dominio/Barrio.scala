package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class BarrioRow(
    id: Long,
    nombre: String,
    municipioId: Long
)

trait BarrioComponent {
  self: MunicipioComponent => //Self es una forma de indicar qué cosas deben existir antes de que exista este trait
  // Definition of the BARRIO table
  class BarrioTable(tag: Tag) extends Table[BarrioRow](tag, "BARRIO") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def nombre = column[String]("NOMBRE")
    def municipioId = column[Long]("MUNICIPIO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        nombre,
        municipioId
      ) <> (BarrioRow.tupled, BarrioRow.unapply)
    // Tiene FK
    def municipio = foreignKey("MUNICIPIO_FK", municipioId, municipios)(_.id)
  }
  lazy val barrios = TableQuery[BarrioTable]
}
