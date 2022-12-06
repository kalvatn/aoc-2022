import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val input = getResourceAsText("06.txt")

  timer {
    val part1 = findMarker(input, 4)
    println(part1)
  }
  timer {
    val part2 = findMarker(input, 14)
    println(part2)
  }
}

fun findMarker(
  input: String,
  size: Int
) = input.windowed(size)
  .indexOfFirst {
    it.toSet()
      .count() == size
  } + size
