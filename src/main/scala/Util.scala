import scala.math.log10
import scala.math.pow
import scala.math.sqrt
import scala.math.abs

object Util {

  // given a list of As and a comparator (A, A) -> Int, get biggest A
  def max[A](list: List[A], comparator: (A, A) => Int): A = list.length match {
    case 1 => list(0)
    case default => if (comparator(list(0),list(1)) > 0) max(list.filter(_ != list(1)), comparator) else max(list.filter(_ != list(0)), comparator)
  }

  // given a list of As, translate each A to B and each B to C
  def map[A,B,C](list: List[A], _AtoB: (A) => B, _BtoC: (B) => C): List[C] =
    list.map(a => _AtoB(a)).map(b => _BtoC(b))

  // given an list of As and a comparator, return true if list is sorted
  def isSorted[A](list: List[A], comparator: (A, A) => Boolean): Boolean = list.length match {
    case 1 => true
    case default => if (comparator(list(0),list(1))) isSorted(list.filter(_ != list(0)), comparator) else false
  }

  // given an array of doubles, find probability for each i in array to appear
  def probs(array: Array[Double]): Array[Double] = 
    array.map(a => array.groupBy(identity).mapValues(_.size / array.length.toDouble)(a) ) 

  // calculate entropy from given array
  def entropy(array: Array[Double]): Double = {
    -probs(array).map(p => p * (log10(p) / log10(2.0)) ).sum
  }

  // calculate expected value from given array
  def mu(array: Array[Double]): Double = 
    array.map(a => a * (1 / array.length.toDouble) ).sum

  // calculate variance from given array
  def variance(array: Array[Double]): Double = 
    array.map(a => (1 / array.length.toDouble) * pow( a - mu(array), 2 ) ).sum

  // calculate z-score from given array
  def zscore(array: Array[Double], x: Double) = 
    (x - mu(array)) / sqrt(variance(array))

  // calculate covariance from two arrays
  def cov(arrayX: Array[Double], arrayY: Array[Double]): Double =
    mu(arrayX.zipWithIndex.map(a => (a._1 - mu(arrayX)) * (arrayY(a._2) - mu(arrayY)) ))

  // calculate pearson correlation from two arrays
  def pearson(arrayX: Array[Double], arrayY: Array[Double]): Double =
    cov(arrayX, arrayY) / ( sqrt(variance(arrayX)) * sqrt(variance(arrayY)) )

  // produce a map of best correlations for each feature in a timeseries
  def getBestCorrelations(data: TimeSeries): Map[String,String] =
    data.features.zipWithIndex.map(feature => (feature._1, data.features.drop(feature._2 + 1).map(cofeature => (cofeature, abs(Util.pearson( data.getValues(feature._1).get.toArray, data.getValues(cofeature).get.toArray ))) ).toMap match { case m if (m.size == 0) => ""; case m => m.maxBy(_._2) match { case max if (max._2 < 0.9) => ""; case max => max._1 } } ) ).toMap

}