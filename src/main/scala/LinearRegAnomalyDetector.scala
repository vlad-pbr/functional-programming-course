import scala.collection.mutable

object LinearRegAnomalyDetector extends AnomalyDetector {

  val Delimiter = "_"

  override def learn(normal: TimeSeries): Map[String, String] = 
    Util.getBestCorrelations(normal, 0.9, 1.0).map(featurePair => featurePair match { case p if (p._2 == "") => p; case p => (p._1, {
    
      val points = Util.buildPoints(normal, p._1, p._2)
      val line = new Line(points)
      val maxDist = points.map(point => line.dist(point)).max

      List(p._2, line.a.toString, line.b.toString, maxDist.toString).mkString(Delimiter)

    } ) } )

  override def detect(model: Map[String, String], test: TimeSeries): Vector[(String, Int)] = 
    test.features.filter(feature => model(feature) != "").map(feature => {
    
      val values = model(feature).split(Delimiter)
      val points = Util.buildPoints(test, feature, values(0))
      val line = new Line(Array[Point](), Option(values(1).toDouble), Option(values(2).toDouble))
      val maxDist = values(3).toDouble

      Vector.empty ++ points.zipWithIndex.collect{ case (p, i) if (line.dist(p) > maxDist) => (feature + "," + values(0), i) }

    } ).flatten
    
}
