import math.abs

class Line(val points: Array[Point], private val _a: Option[Double] = None, private val _b: Option[Double] = None) {

	val a = if (!_a.isEmpty) _a.get else Util.cov(points.map(p => p.x), points.map(p => p.y)) / Util.variance(points.map(p => p.x))
	val b = if (!_b.isEmpty) _b.get else Util.mu(points.map(p => p.y)) - (a * Util.mu(points.map(p => p.x)) )

	def f(x: Double): Double = 
		(a * x) + b

	def dist(point: Point): Double = 
		abs(f(point.x) - point.y)

}