import tools.getResourceAsText
import tools.timer
import kotlin.math.abs
import kotlin.math.sign


enum class Dir(val diff: Pair<Int, Int>) {
  U(Pair(-1, 0)),
  D(Pair(1, 0)),
  R(Pair(0, 1)),
  L(Pair(0, -1))
}

fun main() = timer {
  val input = getResourceAsText("09_test2.txt")

  val moves = input.lines().map { line ->
    val (dirChar, count) = line.split(" ")
    val dir = Dir.values().first { it.name == dirChar }
    Pair(dir, count.toInt())
  }

  timer {
    val tailPositions = mutableListOf<Pair<Int, Int>>()
    var headPos = Pair(0, 0)
    var tailPos = Pair(0, 0)
    tailPositions.add(tailPos)
    moves.forEach { (dir, count) ->
      repeat(count) {
        headPos += dir.diff
        if (tailPos.tooFar(headPos)) {
          tailPos += tailPos.moveTowards(headPos)
          tailPositions += tailPos
        }
      }
    }
    val part1 = tailPositions.toSet().count()
    println(part1)
//    check(part1 == 6332)
  }
  timer {
    val tailPositions = mutableListOf<Pair<Int, Int>>()
    val positions = (1..9).map { Pair(0,0) }.toMutableList()
    check(positions.size == 9)
    moves.forEach { (dir, count) ->
      repeat(count) {
        positions[0] += dir.diff
        (positions.indices.drop(1)).map {i ->
          if (positions[i].tooFar(positions[i-1])) {
            positions[i] += positions[i].moveTowards(positions[i-1])
            if (i == 8) {
              tailPositions += positions[i]
            }
          }
        }
      }
    }
    val part2 = tailPositions.toSet().count()
    println("part2 11974 too high")
    println(part2)
  }
}

private fun Pair<Int, Int>.moveTowards(headPos: Pair<Int, Int>): Pair<Int, Int> {
  val (hy, hx) = headPos
  val (ty, tx) = this
  return Pair((hy - ty).sign, (hx - tx).sign)
}

private fun Pair<Int, Int>.diff(other: Pair<Int, Int>): Int {
  return abs(this.first - other.first) + abs(this.second - other.second)
}

private fun Pair<Int, Int>.tooFar(other: Pair<Int, Int>): Boolean {
  return abs(this.first - other.first) > 1 || abs(this.second - other.second) > 1
}

operator fun Pair<Int, Int>.plus(diff: Pair<Int, Int>): Pair<Int, Int> {
  return Pair(this.first + diff.first, this.second + diff.second)
}

private operator fun Pair<Int, Int>.minus(diff: Pair<Int, Int>): Pair<Int, Int> {
  return Pair(this.first - diff.first, this.second - diff.second)
}
