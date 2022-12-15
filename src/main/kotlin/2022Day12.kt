import tools.getResourceAsText
import tools.timer


//private enum class Dir(val dxy: Pair<Int, Int>) {
//  U(Pair(-1, 0)),
//  D(Pair(1, 0)),
//  L(Pair(0, -1)),
//  R(Pair(0, 1))
//}

typealias P2D = Pair<Int, Int>

fun main() = timer {
  val input = getResourceAsText("12.txt")


  val hMap = ('a'..'z').zip(1..26).toMap()
  val grid = input.replace('S', 'a').replace('E', 'z').lines()
  val goal = findPoint(input.lines(), 'E')

  fun bestFromStart(start: P2D, goal: P2D): Int {

    val seen = mutableSetOf<P2D>()
    val queue = ArrayDeque<Pair<P2D, List<P2D>>>()
    queue.add(Pair(start, listOf()))
    var best = Pair(Int.MAX_VALUE, listOf<P2D>())
    var steps = 0
    while (queue.isNotEmpty()) {
      val (current, path) = queue.removeFirst()
      if (path.size > best.first) {
        continue
      }

//      if (++steps % 1000 == 0) {
//        println("steps : $steps queue : ${queue.size} best: $best")
//      }

      if (current == goal) {
        best = if (path.size < best.first) Pair(path.size, path) else best
      }

      if (current !in seen) {
        Dir.values().map { current + it.diff }
          .filter { it.first in grid.indices && it.second in grid[0].indices }
          .filter { (y, x) ->
            hMap[grid[y][x]]!! - hMap[grid[current.first][current.second]]!! <= 1
          }
          .map {
            queue.add(Pair(it, path + current))
          }
      }
      seen.add(current)
    }
    return best.first
  }

  val start = findPoint(input.lines(), 'S')

  val part1 = bestFromStart(start, goal)
  println(part1)
  check(350 == part1)

  val starts = findPoints(input.lines(), 'a')

  val part2 = starts.minOfOrNull {
    bestFromStart(it, goal)
  }
  println(part2)
  check(349 == part2)



  timer {
  }
  timer {
  }
}

private fun findPoints(grid: List<String>, char: Char): Set<P2D> =
  grid.mapIndexed { index, s ->
    index to s.indexOfFirst { it == char }
  }.mapNotNull { (y, x) ->
    if (x >= 0) y to x else null
  }.toSet()

private fun findPoint(grid: List<String>, char: Char): P2D =
  grid.mapIndexed { index, s ->
    index to s.indexOfFirst { it == char }
  }.firstNotNullOf { (y, x) ->
    if (x >= 0) y to x else null
  }
