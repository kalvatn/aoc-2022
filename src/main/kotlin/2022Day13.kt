import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import tools.getResourceAsText
import tools.timer
import java.lang.IllegalArgumentException

fun main() = timer {
  val input = getResourceAsText("13.txt")
  val pairs = input.split("\n\n").map { it.lines() }

  fun isArray(s: String): Boolean =
    try {
      Json.parseToJsonElement(s).jsonArray.map { it.toString() }
      true
    } catch (e: IllegalArgumentException) {
      false
    }

  fun compare(left: String, right: String): Int {
    return when {
      isArray(left) && !isArray(right) -> compare(left, "[$right]")
      !isArray(left) && isArray(right) -> compare("[$left]", right)
      isArray(left) && isArray(right)  -> {
        val a1 = Json.parseToJsonElement(left).jsonArray.map { it.toString() }
        val a2 = Json.parseToJsonElement(right).jsonArray.map { it.toString() }
        val zipped = a1.zip(a2)
        for ((first, second) in zipped) {
          if (compare(first, second) > 0) return 1
          if (compare(first, second) < 0) return -1
        }
        return a1.size.compareTo(a2.size)
      }

      else                             -> left.toInt().compareTo(right.toInt())
    }
  }

  val part1 = pairs.mapIndexed { index, (left, right) ->
    if (compare(left, right) <= 0) index.inc() else 0
  }.sum()
  println(part1)
  check(part1 == 5588)

  val dividers = listOf("[[2]]", "[[6]]")
  val sorted = (input.lines() + dividers)
    .filter { it.isNotBlank() }
    .sortedWith { o1, o2 -> compare(o1, o2) }
  val part2 = dividers.map { sorted.indexOf(it).inc() }
    .reduce { acc, i -> acc * i }
  println(part2)
  check(part2 == 23958)
}
