package model

import java.sql.Timestamp

import model.dominio._
import slick.jdbc.SQLiteProfile.api._

case class UsuarioRow(
                     id: Long,
                     nombre1: String,
                     nombre2: String,
                     apellido1: String,
                     apellido2: String,
                     celular: String,
                     correo: String,
                     fechaRegistro: Timestamp,
                     generoID: Long,
                     rangoEdadID: Long,
                     ocupacionID: Long,
                     barrioID: Long,
                     municipioID: Long
                   )

trait UsuarioComponent{
  self: UsuarioComponent
    with GeneroComponent
    with RangoEdadComponent
    with OcupacionComponent
    with BarrioComponent
    with MunicipioComponent =>
  // Definition of the USUARIO table
  class UsuarioTable(tag: Tag) extends Table[UsuarioRow] (tag, "USUARIO") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def nombre1 = column[String]("NOMBRE1")
    def nombre2 = column[String]("NOMBRE2")
    def apellido1 = column[String]("APELLIDO1")
    def apellido2 = column[String]("APELLIDO2")
    def celular = column[String]("CELULAR")
    def correo = column[String]("CORREO")
    def fechaRegistro = column[Timestamp]("FECHA_REGISTRO")
    def generoID = column[Long]("GENERO_ID")
    def rangoEdadID = column[Long]("RANGO_EDAD_ID")
    def ocupacionID = column[Long]("OCUPACION_ID")
    def barrioID = column[Long]("BARRIO_ID")
    def municipioID = column[Long]("MUNICIPIO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (
      id,
      nombre1,
      nombre2,
      apellido1,
      apellido2,
      celular,
      correo,
      fechaRegistro,
      generoID,
      rangoEdadID,
      ocupacionID,
      barrioID,
      municipioID
    )<>(UsuarioRow.tupled, UsuarioRow.unapply)
    // Tiene FK
    def genero = foreignKey("GENERO_FK", generoID, generos)(_.id)
    def rangoEdad = foreignKey("RANGO_EDAD_FK", rangoEdadID, rangosEdad)(_.id)
    def ocupacion = foreignKey("OCUPACION_FK", ocupacionID, ocupaciones)(_.id)
    def barrio = foreignKey("BARRIO_FK", barrioID, barrios)(_.id)
    def municipio = foreignKey("MUNICIPIO_FK", municipioID, municipios)(_.id)
  }
  val usuarios = TableQuery[UsuarioTable]
}