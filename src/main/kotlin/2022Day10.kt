import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val input = getResourceAsText("10.txt")

  val lines = input.lines()

  val queue = ArrayDeque<Int>()
  queue.add(0)
  var x = 1L

  val pixels = (0 until 6).map { (0 until 40).map { ' ' }.toMutableList() }.toList()

  var row = 0


  val vals = mutableListOf<Long>()
  for (cycle in 1..240) {
    val parts = lines[cycle.dec() % lines.size].split(" ")
    val pixelPos = cycle.dec() % 40
    x += queue.removeFirst()
    pixels[row][pixelPos] = if (pixelPos in (x.dec()..x.dec() + 2)) '█' else ' '
    if (cycle % 40 == 0 && row < pixels.size) {
      row++
    }
    when (parts.first()) {
      "noop" -> queue.addLast(0)
      "addx" -> queue.addAll(listOf(0, parts[1].toInt()))
    }
    if (cycle in listOf(20, 60, 100, 140, 180, 220)) {
      val strength = cycle * x
      vals += strength
      println("$cycle $x $strength")
    }
  }
  val part1 = vals.sum()
  println(part1)
  check(part1 == 12460L)

  val part2 = "EZFPRAKL"
  pixels.print()
  check(part2 == "EZFPRAKL")
}

fun <T> List<List<T>>.print() {
  this.forEach {
    println(it.joinToString(""))
  }
}
