package model.dominio

import slick.jdbc.SQLiteProfile.api._

case class RangoEdadRow(id: Long, descripcion: String, edadInicio: Int, edadFin: Int)

trait RangoEdadComponent{
  // Definition of the RANGOEDAD table
  class RangoEdadTable(tag: Tag) extends Table[RangoEdadRow] (tag, "RANGO_EDAD") {
    def id = column[Long] ("ID", O.PrimaryKey, O.AutoInc)
    def nombre  = column[String]("NOMBRE")
    def edadInicio = column[Int]("EDAD_INICIO")
    def edadFin = column[Int]("EDAD_FIN")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, nombre, edadInicio, edadFin)<>(RangoEdadRow.tupled, RangoEdadRow.unapply _)
  }
  val rangosEdad = TableQuery[RangoEdadTable]
}