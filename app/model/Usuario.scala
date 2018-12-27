package model

import java.sql.Timestamp

import javax.inject.Inject
import model.dominio._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class UsuarioRow(
    id: Long,
    nombre1: String,
    nombre2: Option[String],
    apellido1: String,
    apellido2: Option[String],
    celular: Option[String],
    correo: String,
    fechaRegistro: Timestamp,
    generoId: Long,
    rangoEdadId: Option[Long],
    ocupacionId: Option[Long],
    barrioId: Option[Long],
    municipioId: Long
)

trait UsuarioComponent {
  self: GeneroComponent
    with RangoEdadComponent
    with OcupacionComponent
    with BarrioComponent
    with MunicipioComponent =>
  // Definition of the USUARIO table
  protected class UsuarioTable(tag: Tag)
      extends Table[UsuarioRow](tag, "USUARIO") {
    def id = column[Long]("ID", O.PrimaryKey) //Cedula de la persona
    def nombre1 = column[String]("NOMBRE1")
    def nombre2 = column[Option[String]]("NOMBRE2")
    def apellido1 = column[String]("APELLIDO1")
    def apellido2 = column[Option[String]]("APELLIDO2")
    def celular = column[Option[String]]("CELULAR")
    def correo = column[String]("CORREO")
    def fechaRegistro = column[Timestamp]("FECHA_REGISTRO")
    def generoId = column[Long]("GENERO_ID")
    def rangoEdadId = column[Option[Long]]("RANGO_EDAD_ID")
    def ocupacionId = column[Option[Long]]("OCUPACION_ID")
    def barrioId = column[Option[Long]]("BARRIO_ID")
    def municipioId = column[Long]("MUNICIPIO_ID")
    // Every table needs a * projection with the same type as the table's type parameter
    def * =
      (
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
      ) <> (UsuarioRow.tupled, UsuarioRow.unapply)
    // Tiene FK
    def genero = foreignKey("GENERO_FK", generoId, generos)(_.id)
    def rangoEdad = foreignKey("RANGO_EDAD_FK", rangoEdadId, rangosEdad)(_.id)
    def ocupacion = foreignKey("OCUPACION_FK", ocupacionId, ocupaciones)(_.id)
    def barrio = foreignKey("BARRIO_FK", barrioId, barrios)(_.id)
    def municipio = foreignKey("MUNICIPIO_FK", municipioId, municipios)(_.id)

    def correoIndex = index("CORREO_IDX", correo, unique = true) // Index para que el correo sea único.

  }
  lazy val usuarios = TableQuery[UsuarioTable]
}

class UsuarioDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getUsuarioById(id: Long): Future[Option[UsuarioRow]] = {
    val q = usuarios.filter(_.id === id) // usuario => usuario.id === id
//    val r: Future[Option[UsuarioRow]] = db.run(q.result.headOption) // Retorna un futuro algo (puede ser nulo, entonces ponemos Option) Y como queremos el primero, ponemos head, pero como posible que sea nul, ponemos headOption
    db.run(q.result.headOption)
  }

  def getUsuariosBySesion(sesionId: Long): Future[Seq[UsuarioRow]] = { // Aquí está implicada la tabla Usuarios, la tabla Asistencia y la tabla Sesión
    val q = for {
      u <- usuarios
      a <- asistencias
      if a.sesionId === sesionId && u.id === a.usuarioId
    } yield u
    db.run(q.result)
  }


}

