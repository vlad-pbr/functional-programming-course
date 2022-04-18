import scala.collection.mutable
import math.abs

val Delimiter = "_"

object LinearRegAnomalyDetector extends AnomalyDetector {
  override def learn(normal: TimeSeries): Map[String, String] = 
    Util.getBestCorrelations(normal).map(featurePair => featurePair match { case p if (p._2 == "") => p; case p => (p._1, {
    
      val points = normal.getValues(p._1).get.zipWithIndex.map(value => Point(value._1, normal.getValues(p._2).get(value._2) ) ).toArray
      val line = Line(points)
      val maxDist = points.map(point => line.dist(point)).max

      println(line.a.toString)

      List(p._2, line.a.toString, line.b.toString, maxDist.toString).mkString(Delimiter)

    } ) } )

  override def detect(model: Map[String, String], test: TimeSeries): Vector[(String, Int)] = 
    test.features.filter(feature => model(feature) != "").map(feature => {
    
      val values = model(feature).split(Delimiter)
      val points = test.getValues(feature).get.zipWithIndex.map(value => Point(value._1, test.getValues(values(0)).get(value._2)) ).toArray
      val line = Line(Array[Point](), Option(values(1).toDouble), Option(values(2).toDouble))
      val maxDist = values(3).toDouble

      Vector.empty ++ points.zipWithIndex.collect{ case (p, i) if (line.dist(p) > maxDist) => (feature + "," + values(0), i) }

    } ).flatten
    
}
