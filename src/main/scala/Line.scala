class Line(val points: Array[Point]) {

	val a = Util.cov(points.map(p => p.x), points.map(p => p.y)) / Util.variance(points.map(p => p.x))
	val b = Util.mu(points.map(p => p.y)) - (a * Util.mu(points.map(p => p.x)) )

	def f(x: Double): Double = 
		(a * x) + b

	def dist(point: Point): Double = {0.0}
}