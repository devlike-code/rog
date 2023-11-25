package rog.gameplay
import rog.gameplay.procgen.PerlinNoise
import breeze.util.Index
import rog.gameplay.Types.PointList
import rog.gameplay.Pos

package object Types {
  type SamplePoint[T] = ((Int, Int), T)
  type PointList[T] = IndexedSeq[Types.SamplePoint[T]]
  type PointGrid[T] = IndexedSeq[IndexedSeq[Option[T]]]
  type PositionGrid[T] = IndexedSeq[(Int, Int)]
}

trait Sample[T] {
  def get(): Types.SamplePoint[T]
  def points(): Types.PointGrid[T]
  def voids(): Types.PositionGrid[T]
  def flatten(): Types.PointList[T]
  def filter(fn: Types.SamplePoint[T] => Boolean): Sample[T]
  def foreach(fn: Types.SamplePoint[T] => Unit): Unit =
    flatten().foreach(fn)
}

class Points[T](pts: Types.PointGrid[T]) extends Sample[T] {
  override def toString(): String = pts.toString()
  val rand = new scala.util.Random()

  def get(): ((Int, Int), T) = {
    val flat = flatten()
    flat(rand.nextInt(flat.length))
  }

  def points(): Types.PointGrid[T] = pts

  def voids(): Types.PositionGrid[T] =
    pts.zipWithIndex.flatMap { case (row, i) =>
      row.zipWithIndex.flatMap {
        case (None, j) => Some((i, j))
        case _         => None
      }
    }

  def flatten(): Types.PointList[T] =
    pts.zipWithIndex.flatMap { case (row, i) =>
      row.zipWithIndex.flatMap {
        case (Some(value), j) => Some((i, j), value)
        case (None, j)        => None
      }
    }

  def filter(fn: Types.SamplePoint[T] => Boolean): Sample[T] =
    new Points(pts.zipWithIndex.map { case (row, i) =>
      row.zipWithIndex.map {
        case (Some(value), j) =>
          if (fn((i, j), value)) {
            Some(value)
          } else {
            None
          }
        case (None, j) => None
      }
    })
}

case class Grid(width: Int, height: Int)
    extends Points[Double](PerlinNoise(width, height).map(_.map(Some(_)))) {

  def under(n: Double): Sample[Double] =
    this.filter { case (pos, v) => v < n }

  def over(n: Double): Sample[Double] =
    this.filter { case (pos, v) => v > n }
}
