package rog.gameplay.procgen

sealed case class Point[A: Fractional](p: (A, A)) {
  import Fractional.Implicits._

  def x = p._1
  def y = p._2

  def scale(weight: A)(implicit ev: Numeric[A]): A = {
    (ev.fromInt(1) - weight) * x + weight * y
  }

  def *(other: Point[A]): A =
    x * other.x + y * other.y
}

package object conversions {
  implicit def tupleToPoint[A: Fractional](p: (A, A)): Point[A] =
    Point(p)
}

// dev.yatsukha perlin-noise found at https://github.com/yatsukha/perlin-noise
object PerlinNoise {
  import conversions._

  def smoothStep(x: Double): Double =
    x * x * (3 - 2 * x)

  def exaggerate(
      x: Double,
      a: Double = 1.85,
      limits: (Double, Double) = (-1.0, 1.0)
  ): Double =
    math.max(math.min(x * a, limits._2), limits._1)

  def marble(noise: Double)(xy: (Double, Double))(
      noiseStrength: Double = 2.0,
      period: (Double, Double) = (5.0, 10.0)
  ): Double =
    (1 + math.sin(
      (noise * noiseStrength + xy._1 * period._1 + xy._2 * period._2) * math.Pi
    )) / 2

  def noise(
      g: IndexedSeq[IndexedSeq[(Double, Double)]],
      p: (Double, Double)
  ): Double = {
    val bx = (p.x.toInt, (p.x + 1).toInt)
    val by = (p.y.toInt, (p.y + 1).toInt)

    val dx = (p.x - bx._1, p.x - bx._2)
    val dy = (p.y - by._1, p.y - by._2)

    val w = (smoothStep(dx._1), smoothStep(dy._1))

    var vx0 = g(bx._1)(by._1) * (dx._1, dy._1)
    var vx1 = g(bx._2)(by._1) * (dx._2, dy._1)

    val vy0 = (vx0, vx1).scale(w.x)

    vx0 = g(bx._1)(by._2) * (dx._1, dy._2)
    vx1 = g(bx._2)(by._2) * (dx._2, dy._2)

    val vy1 = (vx0, vx1).scale(w.x)

    (vy0, vy1).scale(w.y)
  }

  def smooth(
      n: IndexedSeq[IndexedSeq[Double]]
  )(xy: (Double, Double)): Double = {
    val indices = (xy._1.toInt, xy._2.toInt)
    val distance = (xy._1 - indices._1.toDouble, xy._2 - indices._2.toDouble)

    val dxdy = Vector(
      (0, 0, 1 - distance._1, 1 - distance._2),
      (0, 1, 1 - distance._1, distance._2),
      (1, 0, distance._1, 1 - distance._2),
      (1, 1, distance._1, distance._2)
    )

    var value = 0.0

    for (idx <- dxdy) {
      val pos = (indices._1 + idx._1, indices._2 + idx._2)
      value =
        value + n(pos._1 % n.length)(pos._2 % n.head.length) * idx._3 * idx._4
    }

    value
  }

  def turbulence(n: IndexedSeq[IndexedSeq[Double]], s: Int)(
      xy: (Int, Int)
  ): Double = {
    var counter = s
    var value = 0.0

    while (counter >= 1) {
      value = value + smooth(n)(
        xy._1.toDouble / counter.toDouble,
        xy._2.toDouble / counter.toDouble
      ) * counter

      counter = counter / 2
    }

    value / (2 * s.toDouble)
  }

  def gradientStream(rng: scala.util.Random): Stream[(Double, Double)] = {
    val x = rng.between(-1.0, 1.0)
    var y = rng.between(-1.0, 1.0)

    val len = math.sqrt(x * x + y * y)

    if (len > 1.0)
      gradientStream(rng)
    else
      (x / len, y / len) #:: gradientStream(rng)
  }

  def gradientField(
      rng: scala.util.Random,
      dim: (Int, Int)
  ): IndexedSeq[IndexedSeq[(Double, Double)]] = {

    val stream = gradientStream(rng)
    Vector(
      (for (i <- 0 until dim._1)
        yield stream.drop(i * dim._2).take(dim._2).toVector): _*
    )
  }

  def apply(width: Int, height: Int): IndexedSeq[IndexedSeq[Double]] = {
    val noiseField = PerlinNoise.gradientField(
      new scala.util.Random(System.currentTimeMillis()),
      (width + 1, height + 1)
    )

    var angleMap = noiseField.map(_.map { case (x, y) => Math.atan2(y, x) })
    val minElement = angleMap.flatten.min
    val maxElement = angleMap.flatten.max - minElement
    angleMap = angleMap.map(_.map(_ - minElement).map(_ / maxElement))
    angleMap
  }
}
