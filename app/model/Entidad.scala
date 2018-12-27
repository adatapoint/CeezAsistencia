package model

import java.sql.Timestamp

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class EntidadRow(
    id: Option[Long],
    nombre: String,
    sigla: Option[String],
    telefono: Option[String],
    correo: Option[String],
    web: Option[String],
    direccion: Option[String],
    usuarioId: Option[Long]
)

trait EntidadComponent {
  self: UsuarioComponent =>
  // Definition of the ENTIDAD table
  class EntidadTable(tag: Tag) extends Table[EntidadRow](tag, "ENTIDAD") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def nombre = column[String]("NOMBRE")
    def sigla = column[Option[String]]("SIGLA")
    def telefono = column[Option[String]]("TELEFONO")
    def correo = column[Option[String]]("CORREO")
    def web = column[Option[String]]("WEB")
    def direccion = column[Option[String]]("DIRECCION")
    def usuarioId = column[Option[Long]]("USUARIO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
        id.?,
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

class EntidadDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getEntidadById(id: Long): Future[Option[EntidadRow]] = {
    val q = entidades.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
