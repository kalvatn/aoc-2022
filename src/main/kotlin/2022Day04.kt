import tools.getResourceAsText
import tools.timer


fun main() = timer {
  val input = getResourceAsText("04.txt")

  val assignments = input.lines()
    .map { line ->
      val sections = line.split(",")
        .flatMap { section ->
          section.split("-")
            .map { it.toInt() }
        }

      Pair((sections[0]..sections[1]).toSet(), (sections[2]..sections[3]).toSet())
    }

  timer {
    val part1 = assignments.count { (first, second) ->
      first.containsAll(second) || second.containsAll(first)
    }
    println(part1)
  }

  timer {
    val part2 = assignments.count { (first, second) ->
      first.intersect(second)
        .isNotEmpty()
    }
    println(part2)
  }
}
