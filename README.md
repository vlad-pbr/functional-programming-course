# Functional Programming Course

Functional programming course assignment, College of Management Academic Studies, 3rd Year

## Milestone #1

Create an object called `Util` and implement the following statistics related functions using functional programming paradigm:

- max[A](list: List[A], comparator: (A, A) => Int): A
- map[A,B,C](list: List[A], _AtoB: (A) => B, _BtoC: (B) => C): List[C]
- isSorted[A](list: List[A], comparator: (A, A) => Boolean): Boolean
- probs(array: Array[Double]): Array[Double]
- entropy(array: Array[Double]): Double
- mu(array: Array[Double]): Double
- variance(array: Array[Double]): Double
- zscore(array: Array[Double], x: Double)
- cov(arrayX: Array[Double], arrayY: Array[Double]): Double
- pearson(arrayX: Array[Double], arrayY: Array[Double]): Double

Create a class called `Point` which represents a 2D point
Create a class called `Line` which represents a linear regression line, can construct one using an array of `Points` and has the following methods:

- f(x: Double): Double
- dist(point: Point): Double
