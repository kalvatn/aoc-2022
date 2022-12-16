import tools.getResourceAsText
import tools.timer
import kotlin.math.max
import kotlin.math.min

fun main() = timer {
  val input = getResourceAsText("14.txt").lines()

  val rockPaths = input.map { path ->
    val segments = path.split(" -> ")
    val points = segments.map { segment ->
      segment.split(",").map { it.toInt() }.let { (x, y) ->
        y to x
      }
    }
    points
  }
  val rockCoords = rockPaths.flatMap { p ->
    p.drop(1).fold(Pair(p.first(), setOf<P2D>())) { acc, point ->
      val (y1, x1) = acc.first
      val (y2, x2) = point
      val yRange = min(y1, y2)..max(y1, y2)
      val xRange = min(x1, x2)..max(x1, x2)
      point to (acc.second + yRange.flatMap { y ->
        xRange.map { x ->
          y to x
        }
      }.toSet())
    }.second
  }

  val yMax = rockCoords.maxBy { it.first }.first
  val xMax = rockCoords.maxBy { it.second }.second
  val grid = (0..yMax).map { y ->
    (0..xMax).map { x ->
      if (y to x in rockCoords) '#' else '.'
    }.toMutableList()
  }

  fun print(sand: P2D) {
    (grid.indices).map { y ->
      (grid[0].indices).map { x ->
        if (sand.first == y && sand.second == x) 'o' else grid[y][x]
      }.joinToString("")
    }.dropLastWhile { !it.contains("o") }.map { it.drop(450) }.map {
      println(it)
    }
  }


  val sandStart = P2D(0, 500)
  var sand = sandStart
  val DR = Dir.D + Dir.R
  val DL = Dir.D + Dir.L
  val D = Dir.D.diff
  var step = 0
  while (true) {
    val tryD = sand + D
    val tryDL = sand + DL
    val tryDR = sand + DR

    if (tryD.first !in grid.indices) {
      break
    }

    if (++step % 1000 == 0) {
      println("step $step ${grid.sumOf { s -> s.count { it == 'o' } }}")
      print(sand)
    }

    when {
      grid[tryD.first][tryD.second] == '.'   -> sand = tryD
      grid[tryDL.first][tryDL.second] == '.' -> sand = tryDL
      grid[tryDR.first][tryDR.second] == '.' -> sand = tryDR
      else                                   -> {
        grid[sand.first][sand.second] = 'o'
        sand = sandStart
      }
    }
  }
  print(sand)
  val part1 = grid.sumOf { s -> s.count { it == 'o' } }
  println(part1)
}
