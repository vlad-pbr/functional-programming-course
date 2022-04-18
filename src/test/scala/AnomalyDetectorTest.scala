class AnomalyDetectorTest extends munit.FunSuite {

	val timeSeriesTrain = TimeSeries("src/test/resources/train.csv")
	val timeSeriesTest = TimeSeries("src/test/resources/test.csv")

	// ZAnomalyDetector
	test("ZAnomalyDetector") {
		assertEquals(ZAnomalyDetector.detect(ZAnomalyDetector.learn(timeSeriesTrain), timeSeriesTest), Vector(("D", 2)))
	}

	// LinearRegAnomalyDetector
	test("LinearRegAnomalyDetector") {
		assertEquals(LinearRegAnomalyDetector.detect(LinearRegAnomalyDetector.learn(timeSeriesTrain), timeSeriesTest), Vector(("C,D", 2)))
	}

}