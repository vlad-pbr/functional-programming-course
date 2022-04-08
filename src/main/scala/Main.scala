import scala.collection.MapView
import scala.math.log10
@main def hello: Unit = 
  println("Hello world!")

  val nums: List[Int] = List(1, 2, 3, 4)
  val vs = Array(14.0, 14.0, 1.0, 2.0)
  val ps = Array(0.5, 0.5, 0.25, 0.25)
  val xs = Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)

  // Util.max
  println(Util.max(nums, (x: Int, y: Int) => x - y))

  // Util.map
  Util.map(nums,(x:Int)=>x*2,(y:Int)=>"student "+y).foreach(s=>println(s))

  // Util.isSorted
  println("is 'nums' sorted? " + Util.isSorted(nums,(x:Int,y:Int)=>x<=y))

  // Util.probs
  println("do probabilities match? " + Util.probs(vs).sameElements(ps))

  // Util.entropy
  println("is entropy correct? " + (Util.entropy(xs) >= 2.584 && Util.entropy(xs) <= 2.585))

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
}
