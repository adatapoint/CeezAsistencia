package model.dominio

import javax.inject.Inject
import model.MegaTrait
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{ExecutionContext, Future}

case class BarrioRow(
    id: Option[Long],
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
        id.?, // id.? significa que puede ser nulo, dado que no requiere id en el constructor. Pero la base de datos se lo asigna.
        nombre,
        municipioId
      ) <> (BarrioRow.tupled, BarrioRow.unapply)
    // Tiene FK
    def municipio = foreignKey("MUNICIPIO_FK", municipioId, municipios)(_.id)
  }
  lazy val barrios = TableQuery[BarrioTable]
}

class BarrioDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ex: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile]
    with MegaTrait {

  def getBarrioById(id: Long): Future[Option[BarrioRow]] = {
    val q = barrios.filter(_.id === id)
    db.run(q.result.headOption)
  }
}
