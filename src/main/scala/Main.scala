@main def hello: Unit = 
  println("Hello world!")

  val nums: List[Int] = List(1, 2, 3, 4)

  println(Util.max(nums, (x: Int, y: Int) => x - y))
  Util.map(nums,(x:Int)=>x*2,(y:Int)=>"student "+y).foreach(s=>println(s))

object Util {

  // given a list of As and a comparator (A, A) -> Int, get biggest A
  def max[A](list: List[A], comparator: (x: A, y: A) => Int): A = list.length match
    case 1 => list(0)
    case default => if comparator(list(0),list(1)) > 0 then max(list.filter(_ != list(1)), comparator) else max(list.filter(_ != list(0)), comparator)

  // given a list of As, translate each A to B and each B to C
  def map[A,B,C](list: List[A], _AtoB: (a: A) => B, _BtoC: (b: B) => C): List[C] =
    list.map(a => _AtoB(a)).map(b => _BtoC(b))

}