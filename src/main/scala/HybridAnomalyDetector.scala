import scala.collection.mutable

object HybridAnomalyDetector extends AnomalyDetector {

  val Delimiter = "!"

  override def learn(normal: TimeSeries): Map[String, String] = {

    // categorize each feature
    val highCorrelations = Vector.empty ++ Util.getBestCorrelations(normal, 0.9, 1.0).collect{ case(k, v) if (v != "") => k }
    val mediumCorrelations = Vector.empty ++ Util.getBestCorrelations(normal, 0.5, 0.9).collect{ case(k, v) if (!highCorrelations.contains(k) && v != "") => k }
    val noCorrelations = normal.features.filter(feature => !highCorrelations.contains(feature) && !mediumCorrelations.contains(feature) )

    // build maps according to each feature
    val highCorrelationsMap = LinearRegAnomalyDetector.learn(normal).collect{ case feature if (highCorrelations.contains(feature._1)) => (feature._1, LinearRegAnomalyDetector.getClass.getSimpleName + Delimiter + feature._2) }
    val mediumCorrelationsMap = Util.getBestCorrelations(normal, 0.5, 0.9).filter(featurePair => mediumCorrelations.contains(featurePair._1)).map(featurePair => ( featurePair._1, {
    
      val points = Util.buildPoints(normal, featurePair._1, featurePair._2)
      val minSqrSumPoint = points.map(point => (point, Util.sqrSum(points, point))).minBy(_._2)._1
      val maxDist = points.map(point => Util.euclideanDist(minSqrSumPoint, point)).max

      List(featurePair._2, maxDist.toString).mkString(SumSqrAnomalyDetector.Delimiter)

    } ) ).map(featurePair => (featurePair._1, SumSqrAnomalyDetector.getClass.getSimpleName + Delimiter + featurePair._2))
    val noCorrelationsMap = ZAnomalyDetector.learn(normal).collect{ case feature if (noCorrelations.contains(feature._1)) => (feature._1, ZAnomalyDetector.getClass.getSimpleName + Delimiter + feature._2) }

    highCorrelationsMap ++ mediumCorrelationsMap ++ noCorrelationsMap
  }

  override def detect(model: Map[String, String], test: TimeSeries): Vector[(String, Int)] = {

    // decode and classify features
    val highCorrelationsMap = model.collect{ case feature if ( feature._2.startsWith(LinearRegAnomalyDetector.getClass.getSimpleName) ) => (feature._1, feature._2.split(Delimiter)(1) ); case feature => (feature._1, "") }
    val mediumCorrelationsMap = model.collect{ case feature if ( feature._2.startsWith(SumSqrAnomalyDetector.getClass.getSimpleName) ) => (feature._1, feature._2.split(Delimiter)(1) ); case feature => (feature._1, "") }
    val noCorrelationsMap = model.collect{ case feature if ( feature._2.startsWith(ZAnomalyDetector.getClass.getSimpleName) ) => (feature._1, feature._2.split(Delimiter)(1) ); case feature => (feature._1, Double.PositiveInfinity.toString) }

    // detect hits for each appropriate detector
    val highCorrelationsHits = LinearRegAnomalyDetector.detect(highCorrelationsMap, test)
    val mediumCorrelationsHits = test.features.filter(feature => mediumCorrelationsMap(feature) != "").map(feature => {
    
      val values = mediumCorrelationsMap(feature).split(SumSqrAnomalyDetector.Delimiter)
      val points = Util.buildPoints(test, feature, values(0))
      val minSqrSumPoint = points.map(point => (point, Util.sqrSum(points, point))).minBy(_._2)._1

      points.zipWithIndex.collect{ case(p, i) if (Util.euclideanDist(minSqrSumPoint, p) > values(1).toDouble ) => (feature + "," + values(0), i) }

    } ).flatten
    val noCorrelationsHits = ZAnomalyDetector.detect(noCorrelationsMap, test)

    highCorrelationsHits ++ mediumCorrelationsHits ++ noCorrelationsHits
  }

}
