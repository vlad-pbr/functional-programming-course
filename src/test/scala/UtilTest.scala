
class UtilTest extends munit.FunSuite {

  // Util.max
  test("Util.max") {
    val nums: List[Int] = List(1, 2, 3, 4)
    assertEquals(Util.max(nums, (x: Int, y: Int) => x - y), 4)
  }

  // Util.map
  test("Util.map") {
    val nums: List[Int] = List(1, 2, 3, 4)
    val expected: List[String] = List("student 2","student 4","student 6","student 8")
    assertEquals(Util.map(nums,(x:Int)=>x*2,(y:Int)=>"student "+y), expected)
  }

  // Util.isSorted
  test("Util.isSorted") {
    val nums: List[Int] = List(1, 2, 3, 4)
    assertEquals(Util.isSorted(nums,(x:Int,y:Int)=>x<=y), true)
  }

  // Util.probs
  test("Util.probs") {
    val vs = Array(14.0, 14.0, 1.0, 2.0)
    val ps = Array(0.5, 0.5, 0.25, 0.25)
    assertEquals(Util.probs(vs).sameElements(ps), true)
  }

  // Util.entropy
  test("Util.entropy") {
    val xs = Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
    val entropy = Util.entropy(xs)
    assertNotEquals(entropy < 2.584 || entropy > 2.585, true)
  }

  // Util.mu
  test("Util.mu") {
    val xs = Array(1.0, 1.0, 3.0, 4.0, 4.0)
    val mu = Util.mu(xs)
    assertNotEquals(mu < 2.59 || mu > 2.61, true)
  }

  // Util.variance
  test("Util.variance") {
    val xs = Array(1.0, 1.0, 3.0, 4.0, 4.0)
    val variance = Util.variance(xs)
    assertNotEquals(variance < 1.83 || variance > 1.85, true)
  }

  // Util.zscore
  test("Util.zscore") {
    val xs = Array(1.0, 1.0, 3.0, 4.0, 4.0)
    val zscore = Util.zscore(xs, 3.0)
    assertNotEquals(zscore < (0.294) - 0.001 || zscore > (0.294) + 0.001, true )
  }

}