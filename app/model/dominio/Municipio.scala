package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class MunicipioRow(
                         id: Long,
                         nombre: String,
                         departamentoID: Long
                       )

trait MunicipioComponent{
  self: MunicipioComponent with DepartamentoComponent =>
  // Definition of the MUNICIPIO table
  class MunicipioTable(tag: Tag) extends Table[MunicipioRow] (tag, "MUNICIPIO") {
    def id = column[Long] ("ID", O.PrimaryKey) //Municipio ID doesn't autoinc
    def nombre  = column[String]("NOMBRE")
    def departamentoID = column[Long]("DEPARTAMENTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (
      id,
      nombre,
      departamentoID
    )<>(MunicipioRow.tupled, MunicipioRow.unapply)
    // Tiene FK
    def departamento = foreignKey("DEPARTAMENTO_FK", departamentoID, departamentos)(_.id)
  }
  val municipios = TableQuery[MunicipioTable]
}