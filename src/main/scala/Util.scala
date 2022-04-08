import scala.collection.MapView
import scala.math.log10
import scala.math.pow
import scala.math.sqrt

object Util {

  // given a list of As and a comparator (A, A) -> Int, get biggest A
  def max[A](list: List[A], comparator: (x: A, y: A) => Int): A = list.length match
    case 1 => list(0)
    case default => if comparator(list(0),list(1)) > 0 then max(list.filter(_ != list(1)), comparator) else max(list.filter(_ != list(0)), comparator)

  // given a list of As, translate each A to B and each B to C
  def map[A,B,C](list: List[A], _AtoB: (a: A) => B, _BtoC: (b: B) => C): List[C] =
    list.map(a => _AtoB(a)).map(b => _BtoC(b))

  // given an list of As and a comparator, return true if list is sorted
  def isSorted[A](list: List[A], comparator: (x: A, y: A) => Boolean): Boolean = list.length match
    case 1 => true
    case default => if comparator(list(0),list(1)) then isSorted(list.filter(_ != list(0)), comparator) else false

  // given an array of doubles, find probability for each i in array to appear
  def probs(array: Array[Double]): Array[Double] = 
    array.map(a => array.groupBy(identity).mapValues(_.size / array.length.toDouble)(a) ) 

  // calculate entropy from given array
  def entropy(array: Array[Double]): Double =

    def entropyWithProbs(arr: Array[Double], probsMap: MapView[Double, Double]): Double = arr match
      case a if a.length == 0 => 0.0
      case default => ( probsMap(arr(0)) * (log10(probsMap(arr(0))) / log10(2.0) ) ) + entropyWithProbs(arr.filter(_ != arr(0)), probsMap)

    -entropyWithProbs(array, array.groupBy(identity).mapValues(_.size / array.length.toDouble))

  // calculate expected value from given array
  def mu(array: Array[Double]): Double = 
    array.map(a => a * (1 / array.length.toDouble) ).sum

  // calculate variance from given array
  def variance(array: Array[Double]): Double = 
    array.map(a => (1 / array.length.toDouble) * pow( a - mu(array), 2 ) ).sum

  // calculate standard deviation from given variance
  def standard_deviation_from_variance(variance: Double): Double =
    sqrt(variance)

  // calculate z-score from given array
  def zscore(array: Array[Double], x: Double) = 
    (x - mu(array)) / standard_deviation_from_variance(variance(array))

  // calculate covariance from two arrays
  def cov(arrayX: Array[Double], arrayY: Array[Double]): Double =
    mu(arrayX.zipWithIndex.map(a => (a._1 - mu(arrayX)) * (arrayY(a._2) - mu(arrayY)) ))

  // calculate pearson correlation from two arrays
  def pearson(arrayX: Array[Double], arrayY: Array[Double]): Double =
    cov(arrayX, arrayY) / ( standard_deviation_from_variance(variance(arrayX)) * standard_deviation_from_variance(variance(arrayY)) )

}