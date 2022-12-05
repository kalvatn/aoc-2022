import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val input = getResourceAsText("05.txt").split("\n\n")
    .map { it.lines() }

  val moves = parseMoves(input[1])

  timer {
    val stacks = parseStacks(input[0])
    moves.map { (count, from, to) ->
      repeat(count) { stacks[to].add(0, stacks[from].removeFirst()) }
    }
    val part1 = stacks.map { it.first() }.joinToString("")

    check(part1 == "RNZLFZSJH")
    println(part1)
  }
  timer {
    val stacks = parseStacks(input[0])

    moves.map { (count, from, to) ->
      val items = stacks[from].take(count)
      repeat(count) { stacks[from].removeFirst() }
      stacks[to].addAll(0, items)
    }
    val part2 = stacks.map { it.first() }
      .joinToString("")

    check(part2 == "CNSFCGJSM")
    println(part2)
  }
}

private fun parseMoves(lines: List<String>): List<Triple<Int, Int, Int>> {
  val movePattern = Regex("^move (\\d+) from (\\d) to (\\d)$")
  return lines.map { line ->
    val (count, from, to) = movePattern.matchEntire(line)!!.destructured.toList()
      .map { it.toInt() }
    Triple(count, from.dec(), to.dec())
  }
}

private fun parseStacks(lines: List<String>): List<MutableList<Char>> {
  val crates = lines.dropLast(1)
  val stacks = lines.last()
  return stacks
    .mapIndexedNotNull { column, char -> char.digitToIntOrNull()?.let { column } }
    .map { column ->
      crates.mapNotNull { it.getOrNull(column) }.filterNot { it.isWhitespace() }.toMutableList()
    }
}
