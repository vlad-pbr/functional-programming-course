
class PointTest extends munit.FunSuite {

  val points = Array(new Point(0,0.1), new Point(1,2.01), new Point(5.1,10))
  val line = new Line(points)

  // 'a' is calculated correctly
  test("'a' is calculated correctly") {
    assertNotEquals(line.a < 1.93 || line.a > 1.95, true)
  }

  // 'b' is calculated correctly
  test("'b' is calculated correctly") {
    assertNotEquals(line.b < 0.084 || line.b > 0.086, true)
  }
}