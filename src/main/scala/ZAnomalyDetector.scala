import scala.collection.mutable
import math.abs

object ZAnomalyDetector extends AnomalyDetector {

  override def learn(normal: TimeSeries): Map[String, String] = {
    normal.features.map(feature => (feature, (normal.getValues(feature).get.map(value => abs(Util.zscore(normal.getValues(feature).get.toArray, value))).max).toString) ).toMap
  }

  override def detect(model: Map[String, String], test: TimeSeries): Vector[(String, Int)] = 
    test.features.map(feature => test.getValues(feature).get.zipWithIndex.map(v => (feature, v._2, v._1) ) ).flatten.collect{ case(f, i, v) if (abs(Util.zscore(test.getValues(f).get.toArray, v.toString.toDouble)) > model(f).toDouble) => (f, i) }
}
