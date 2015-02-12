package org.uqbar.math.spaces

import scala.collection.mutable.AnyRefMap

/**
 * Este vector tiene una caché para funcionar mucho más rápido en lectura y escritura.
 * La desventaja es que asume que los primeros tres componentes son X, Y y Z, y no es en función del espacio
 */
//TODO: Hacerlo funcionar con otra cosa que no sean doubles
//TODO: Hacerlo funcionar con más dimensiones. Algunas funciones ya están preparadas para ello. Ver qué onda el espacio
class CachedVector(var x:Double, var y:Double,var z : Double = null.asInstanceOf[Double])(implicit sp: CachedSpace) extends GenericVector[Double](Array(x, y, z)) {

  override def componentMap =
    AnyRefMap((Axis.X, x), (Axis.Y, y), (Axis.Z, z))
 
  override val components = Array(x,y,z)

  override def apply(ax: Axis) = ax match {
    case Axis.X => x
    case Axis.Y => y
    case Axis.Z => z
    case ax => super.apply(ax)
  }

  override def set(someComponents: Array[Double]): Unit = {
    val lifted = someComponents.lift
    x = lifted(0).getOrElse(x)
    y = lifted(1).getOrElse(y)
    z = lifted(2).getOrElse(z)
  }
      

  override def set(ax: Axis, v: Double): Unit = ax match {
    case Axis.X => x = v
    case Axis.Y => y = v
    case Axis.Z => z = v
  }

  override def set(v: GenericVector[Double]): Unit = {
    x = v(Axis.X)
    y = v(Axis.Y)
    z = v(Axis.Z)
  }
  
  override def +(x: GenericVector[Double]) = sp.vector(this.x + x(Axis.X), this.y + x(Axis.Y), this.z + x(Axis.Z))

}

