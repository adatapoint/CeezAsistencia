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
                     generoId: Long,
                     rangoEdadId: Long,
                     ocupacionId: Long,
                     barrioId: Long,
                     municipioId: Long
                   )

trait UsuarioComponent{
    self: GeneroComponent
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
    def generoId = column[Long]("GENERO_ID")
    def rangoEdadId = column[Long]("RANGO_EDAD_ID")
    def ocupacionId = column[Long]("OCUPACION_ID")
    def barrioId = column[Long]("BARRIO_ID")
    def municipioId = column[Long]("MUNICIPIO_ID")
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
      generoId,
      rangoEdadId,
      ocupacionId,
      barrioId,
      municipioId
    )<>(UsuarioRow.tupled, UsuarioRow.unapply)
    // Tiene FK
    def genero = foreignKey("GENERO_FK", generoId, generos)(_.id)
    def rangoEdad = foreignKey("RANGO_EDAD_FK", rangoEdadId, rangosEdad)(_.id)
    def ocupacion = foreignKey("OCUPACION_FK", ocupacionId, ocupaciones)(_.id)
    def barrio = foreignKey("BARRIO_FK", barrioId, barrios)(_.id)
    def municipio = foreignKey("MUNICIPIO_FK", municipioId, municipios)(_.id)
  }
  val usuarios = TableQuery[UsuarioTable]
}