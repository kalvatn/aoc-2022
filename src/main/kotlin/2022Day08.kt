import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val input = getResourceAsText("08.txt")

  val grid: List<List<Int>> = input.lines().map {
    it.map { c -> c.digitToInt() }
  }

  val indicesY = grid.indices
  val indicesX = grid[0].indices

  val boundsY = Pair(indicesY.min(), indicesY.max())
  val boundsX = Pair(indicesX.min(), indicesX.max())

  val part1 = indicesY.flatMap { y ->
    indicesX.map { x ->
      val height = grid[y][x]
      val visibleFromTop = (boundsY.first until y).map { grid[it][x] }.all { it < height }
      val visibleFromBottom = (y.inc()..boundsY.second).map { grid[it][x] }.all { it < height }
      val visibleFromLeft = (boundsX.first until x).map { grid[y][it] }.all { it < height }
      val visibleFromRight = (x.inc()..boundsX.second).map { grid[y][it] }.all { it < height }

      visibleFromLeft || visibleFromRight || visibleFromBottom || visibleFromTop
    }
  }.count { it }

  println(part1)
  check(part1 == 1851)

  val part2 = indicesY.flatMap { y ->
    indicesX.map { x ->
      val height = grid[y][x]
      val u = (y.dec() downTo boundsY.first.inc()).takeWhile { grid[it][x] < height }.count().inc()
      val d = (y.inc() until boundsY.second).takeWhile { grid[it][x] < height }.count().inc()
      val l = (x.dec() downTo boundsX.first.inc()).takeWhile { grid[y][it] < height }.count().inc()
      val r = (x.inc() until boundsX.second).takeWhile { grid[y][it] < height }.count().inc()
      u * d * l * r
    }
  }.maxOf { it }
  println(part2)
  check(part2 == 574080)

}
