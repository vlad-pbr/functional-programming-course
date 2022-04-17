class TimeSeriesTest extends munit.FunSuite {

  val timeSeries = TimeSeries("src/test/resources/train.csv")

  // TimeSeries.features
  test("TimeSeries.features") {
    assertEquals(timeSeries.features.toSeq, Vector("A", "B", "C", "D").toSeq)
  }

  // TimeSeries.getValues [timeStep]
  test("TimeSeries.getValues [timeStep]") {
    assertEquals(timeSeries.getValues("B"), Option(Vector(2.0, 3.0, 4.0, 5.0)))
    assertEquals(timeSeries.getValues("E").isEmpty, true)
  }

  // TimeSeries.getValue
  test("TimeSeries.getValue") {
    assertEquals(timeSeries.getValue("A", 0).get, 1.0)
    assertEquals(timeSeries.getValue("A", 4).isEmpty, true)
    assertEquals(timeSeries.getValue("E", 0).isEmpty, true)
    assertEquals(timeSeries.getValue("E", 4).isEmpty, true)
  }

  // TimeSeries.getValues [range]
  test("TimeSeries.getValues [range]") {
    assertEquals(timeSeries.getValues("A", 0 to 2).get, Vector(1.0, 2.0, 3.0))
    assertEquals(timeSeries.getValues("A", 0 to 2 by 2).get, Vector(1.0, 3.0))
    assertEquals(timeSeries.getValues("A", 0 to 10 by 2).isEmpty, true)
    assertEquals(timeSeries.getValues("E", 0 to 2).isEmpty, true)
    assertEquals(timeSeries.getValues("E", 0 to 10).isEmpty, true)
  }

}