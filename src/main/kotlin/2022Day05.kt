import tools.getResourceAsText
import tools.timer

fun main() = timer {
  val input = getResourceAsText("05.txt")
    .split("\n\n")
    .map { it.lines() }

  val moves = parseMoves(input[1])

  timer {
    val stacks = input[0].parseStacks()
    moves.map { (count, from, to) ->
      repeat(count) { stacks[to].add(0, stacks[from].removeFirst()) }
    }
    val part1 = stacks.map { it.first() }.joinToString("")

    check(part1 == "RNZLFZSJH")
    println(part1)
  }
  timer {
    val stacks = input[0].parseStacks()
    moves.map { (count, from, to) ->
      val items = stacks[from].take(count)
      repeat(count) { stacks[from].removeFirst() }
      stacks[to].addAll(0, items)
    }
    val part2 = stacks.map { it.first() }.joinToString("")

    check(part2 == "CNSFCGJSM")
    println(part2)
  }
}

private fun parseMoves(lines: List<String>): List<Triple<Int, Int, Int>> {
  val re = Regex("^move (\\d+) from (\\d) to (\\d)$")
  return lines.map { line ->
    val (count, from, to) = re.matchEntire(line)!!.destructured.toList().map { it.toInt() }
    Triple(count, from.dec(), to.dec())
  }
}

fun List<String>.parseStacks() =
  (1..33 step 4).map { col ->
    mapNotNull { it.getOrNull(col) }
      .filterNot { it.isWhitespace() }
      .toMutableList()
  }
