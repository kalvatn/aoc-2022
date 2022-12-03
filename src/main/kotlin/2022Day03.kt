import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val input = getResourceAsText("03.txt")
  val priority = (('a'..'z').zip((1..26)) + ('A'..'Z').zip((27..52))).toMap()

  timer {
    val compartments = input.lines()
      .map {
        val n = it.length / 2
        Pair(it.take(n), it.takeLast(n))
      }

    val part1 = compartments.sumOf { (c1, c2) ->
      c1.toSet()
        .intersect(c2.toSet())
        .sumOf { priority[it]!! }
    }
    println(part1)
  }

  timer {
    val groups = input.lines()
      .chunked(3)

    val part2 = groups.sumOf { rucksacks ->
      rucksacks.map { it.toSet() }
        .reduce { common, k -> common.intersect(k) }
        .sumOf { priority[it]!! }
    }
    println(part2)
  }
}
