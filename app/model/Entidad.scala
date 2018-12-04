package model

import java.sql.Timestamp

import slick.jdbc.SQLiteProfile.api._

case class EntidadRow(
                     id: Long,
                     nombre: String,
                     sigla: String,
                     telefono: String,
                     correo: String,
                     web: String,
                     direccion: String,
                     usuarioID: Long,
                   )

trait EntidadComponent{
  self: EntidadComponent with UsuarioComponent =>
  // Definition of the ENTIDAD table
  class EntidadTable(tag: Tag) extends Table[EntidadRow] (tag, "ENTIDAD") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def nombre = column[String] ("NOMBRE")
    def sigla = column[String] ("SIGLA")
    def telefono = column[String] ("TELEFONO")
    def correo = column[String] ("CORREO")
    def web = column[String] ("WEB")
    def direccion = column[String] ("DIRECCION")
    def usuarioID = column[Long]("USUARIO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, nombre, sigla, telefono, correo, web, direccion, usuarioID)<>(EntidadRow.tupled, EntidadRow.unapply _)
    // Tiene FK
    def usuario = foreignKey("USUARIO_FK", usuarioID, usuarios)(_.id)
  }
  val entidades = TableQuery[EntidadTable]
}