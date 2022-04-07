@main def hello: Unit = 
  println("Hello world!")

  val nums: List[Int] = List(1, 2, 3, 4)
  println(Util.max(nums, (x: Int, y: Int) => x - y))

object Util {

  // given a list of As and a comparator (A, A) -> Int, get biggest A
  def max[A](list: List[A], comparator: (x: A, y: A) => Int): A = list.length match
    case 1 => list(0)
    case default => if comparator(list(0),list(1)) > 0 then max(list.filter(_ != list(1)), comparator) else max(list.filter(_ != list(0)), comparator)

}
