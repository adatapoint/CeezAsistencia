package model

import java.time.{LocalDate, LocalDateTime}

import model.dominio._
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.SQLiteProfile.api._

/**
  * Este trait une todos los otros trait del modelo
  */
trait MegaTrait
    extends BarrioComponent
    with DepartamentoComponent
    with DifusionComponent
    with EstadoProyectoComponent
    with GeneroComponent
    with MunicipioComponent
    with OcupacionComponent
    with RangoEdadComponent
    with AlianzaComponent
    with AsistenciaComponent
    with CicloComponent
    with EntidadComponent
    with ProyectoComponent
    with SesionComponent
    with TipoSesionComponent
    with UsuarioComponent {}

object MegaTrait extends MegaTrait {
  lazy val megaArraySchema = Array(
    barrios.schema,
    departamentos.schema,
    difusiones.schema,
    estadosProyecto.schema,
    generos.schema,
    municipios.schema,
    ocupaciones.schema,
    rangosEdad.schema,
    alianzas.schema,
    asistencias.schema,
    ciclos.schema,
    entidades.schema,
    proyectos.schema,
    sesiones.schema,
    tiposSesion.schema,
    usuarios.schema
  ).reduceLeft(_ ++ _) // Acumula varios esquemas en un solo objeto

  def getCreateSchema = {
    megaArraySchema.create
  }

}

object ImplicitMappingDB {

  implicit def date2localDate
    : JdbcType[LocalDate] with BaseTypedType[LocalDate] =
    MappedColumnType.base[java.time.LocalDate, java.sql.Date](
      localDate => {
        //println(s"Pasando de localDate a date -> $localDate")
        java.sql.Date.valueOf(localDate)
      },
      date => {
        //println(s"Pasando de date a localDate -> $date")
        date.toLocalDate
      }
    )

  implicit def timestamp2localDateTime
    : JdbcType[LocalDateTime] with BaseTypedType[LocalDateTime] =
    MappedColumnType.base[java.time.LocalDateTime, java.sql.Timestamp](
      localDateTime => {
        //println(s"Pasando de localDateTime a timestamp -> $localDateTime")
        java.sql.Timestamp.valueOf(localDateTime)
      },
      timestamp => {
        //println(s"Pasando de timestamp a LocalDatetime -> $timestamp")
        timestamp.toLocalDateTime
      }
    )

}
