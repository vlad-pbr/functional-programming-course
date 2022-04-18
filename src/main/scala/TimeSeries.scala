import scala.io.Source

class TimeSeries(csvFileName: String) {

  val features = (() => { 
    
    val source = Source.fromFile(csvFileName)
    val values = Vector.empty ++ source.getLines().next().split(",")
    source.close()

    values

  })() 

  private val featureMap = (() => { 
    
    val source = Source.fromFile(csvFileName)
    val values = features.zipWithIndex.map(key => ((key._1, source.reset().getLines().drop(1).zipWithIndex.map(line => (line._2, line._1.split(",")(key._2).toDouble)).toMap))).toMap
    source.close()

    values

  })()

  // given name of a feature return in O(1) its value series
  def getValues(feature: String): Option[Vector[Double]] = features.contains(feature) match {
    case false => None
    case true => Option(Vector.empty ++ featureMap(feature).values)
  }

  // given name of a feature return in O(1) its value at the given time step
  def getValue(feature: String, timeStep: Int): Option[Double] = ( features.contains(feature) && featureMap(feature).keySet.contains(timeStep) ) match {
    case false => None
    case true => Option(featureMap(feature)(timeStep))
  }
  
  // given name of a feature return its value series in the range of indices
  def getValues(feature: String, r: Range): Option[Vector[Double]] = ( features.contains(feature) && r.forall(i => featureMap(feature).keySet.contains(i) ) ) match {
    case false => None
    case true => Option(Vector.empty ++ r.map(i => featureMap(feature)(i) ))
  }

}
