package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class MunicipioRow(
                         id: Long,
                         nombre: String,
                         departamentoId: Long
                       )

trait MunicipioComponent{
  self: DepartamentoComponent =>
  // Definition of the MUNICIPIO table
  class MunicipioTable(tag: Tag) extends Table[MunicipioRow] (tag, "MUNICIPIO") {
    def id = column[Long] ("ID", O.PrimaryKey) //Municipio ID doesn't autoinc
    def nombre  = column[String]("NOMBRE")
    def departamentoId = column[Long]("DEPARTAMENTO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (
      id,
      nombre,
      departamentoId
    )<>(MunicipioRow.tupled, MunicipioRow.unapply)
    // Tiene FK
    def departamento = foreignKey("DEPARTAMENTO_FK", departamentoId, departamentos)(_.id)
  }
  lazy val municipios = TableQuery[MunicipioTable]
}