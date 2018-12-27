package model

import java.sql.Timestamp

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class EntidadRow(
    id: Long,
    nombre: String,
    sigla: String,
    telefono: String,
    correo: String,
    web: String,
    direccion: String,
    usuarioId: Long
)

trait EntidadComponent {
  self: UsuarioComponent =>
  // Definition of the ENTIDAD table
  class EntidadTable(tag: Tag) extends Table[EntidadRow](tag, "ENTIDAD") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def nombre = column[String]("NOMBRE")
    def sigla = column[String]("SIGLA")
    def telefono = column[String]("TELEFONO")
    def correo = column[String]("CORREO")
    def web = column[String]("WEB")
    def direccion = column[String]("DIRECCION")
    def usuarioId = column[Long]("USUARIO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id,
        nombre,
        sigla,
        telefono,
        correo,
        web,
        direccion,
        usuarioId
      ) <> (EntidadRow.tupled, EntidadRow.unapply)
    // Tiene FK dado que las entidades tienen un contacto.
    def usuario = foreignKey("USUARIO_FK", usuarioId, usuarios)(_.id)
  }
  lazy val entidades = TableQuery[EntidadTable]
}

class EntidadDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getEntidadById(id: Long): Future[Option[EntidadRow]] = {
    val q = entidades.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
