import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val input = getResourceAsText("01.txt").split("\n\n")
    .map { group ->
      group.lines()
        .map { it.toInt() }
    }

  timer {
    println(input.maxOfOrNull { it.sum() })
  }
  timer {
    println(input.map { it.sum() }
      .sorted()
      .takeLast(3)
      .sum())
  }
}
