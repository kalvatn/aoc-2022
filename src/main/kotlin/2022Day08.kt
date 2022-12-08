import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val input = getResourceAsText("08.txt")

  val grid = input.lines().map { it.map { c -> c.digitToInt() } }
  val indices = grid.indices
  val max = indices.max()

  val heightsByCoordinate = indices.flatMap { y ->
    indices.map { x ->
      y to x to grid[y][x]
    }
  }.toMap()

  val part1 = heightsByCoordinate.count { (coord, height) ->
    val (y, x) = coord
//    val u = (0 until y).map { grid[it][x] }.all { it < height }
//    val d = (y.inc()..max).map { grid[it][x] }.all { it < height }
//    val l = (0 until x).map { grid[y][it] }.all { it < height }
//    val r = (x.inc()..max).map { grid[y][it] }.all { it < height }
//    u || d || l || r
    listOf(
      mapY(grid, x, (0 until y)),
      mapY(grid, x, (y.inc()..max)),
      mapX(grid, y, (0 until x)),
      mapX(grid, y, (x.inc()..max))
    ).map { heights ->
      heights.all { it < height }
    }.any { it }
  }

  println(part1)
  check(part1 == 1851)

  val part2 = heightsByCoordinate.maxOf { (k, height) ->
    val (y, x) = k
//    val u = (y.dec() downTo 1).takeWhile { grid[it][x] < height }.count().inc()
//    val d = (y.inc() until max).takeWhile { grid[it][x] < height }.count().inc()
//    val l = (x.dec() downTo 1).takeWhile { grid[y][it] < height }.count().inc()
//    val r = (x.inc() until max).takeWhile { grid[y][it] < height }.count().inc()
//    u * d * l * r
    listOf(
      mapY(grid, x, (y.dec() downTo 1)),
      mapY(grid, x, (y.inc() until max)),
      mapX(grid, y, (x.dec() downTo 1)),
      mapX(grid, y, (x.inc() until max)),
    ).map { heights ->
      heights.takeWhile { it < height }.count().inc()
    }.fold(1) { acc, score ->
      acc * score
    }
  }
  println(part2)
  check(part2 == 574080)

}

fun mapY(grid: List<List<Int>>, x: Int, range: IntProgression) = range.map { grid[it][x] }
fun mapX(grid: List<List<Int>>, y: Int, range: IntProgression) = range.map { grid[y][it] }
