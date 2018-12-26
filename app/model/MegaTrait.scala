package model

import model.dominio._
import slick.jdbc.SQLiteProfile.api._

/**
  * Este trait une todos los otros trait del modelo
  */
trait MegaTrait extends BarrioComponent
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
  with UsuarioComponent {
}


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
    usuarios.schema
  ).reduceLeft(_ ++ _) // Acumula varios esquemas en un solo objeto

  def getCreateSchema = {
    megaArraySchema.create
  }

}
