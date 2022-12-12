import tools.getResourceAsText
import tools.timer


enum class Op {
  MUL,
  ADD
}

enum class ArgType {
  OLD,
  NUM
}

data class Arg(val type: ArgType, val number: Long)

data class Monkey(
  val number: Int,
  val items: MutableList<Long>,
  val worryFunction: Pair<Op, Arg>,
  val divisionTest: Long,
  val throwTo: Pair<Int, Int>,
  var count: Long = 0
) {

  fun inspect(): MutableMap<Int, MutableList<Long>> {
    val thrown = mutableMapOf<Int, MutableList<Long>>()
    while (items.isNotEmpty()) {
      val item = items.removeFirst()
      val (op, arg) = worryFunction
      val arg2 = when (arg.type) {
        ArgType.NUM -> arg.number
        ArgType.OLD -> item
      }
      val new = when (op) {
        Op.MUL -> item * arg2
        Op.ADD -> item + arg2
      }.let {
        it / 3
      }
      val to = if (new % divisionTest == 0L) throwTo.first else throwTo.second
      thrown.computeIfAbsent(to) { mutableListOf() }.add(new)
      count++
    }
    return thrown
  }

  override fun toString(): String {
    return "Monkey $number: ${items.joinToString(",")}. inspected items $count times."
  }
}

fun main() = timer {
  val inputs = mapOf(
    "11_test.txt" to Pair(10605L, 2713310158L),
    "11.txt" to Pair(50616L, 0L)
  )
  val inputFile = "11_test.txt"
  val input = getResourceAsText(inputFile)

  val sections = input.split("\n\n")

  val re =
    Regex("""Monkey (\d+):\n\s+Starting items: ([\d,\s]+)\n\s+Operation: new = old ([*+]) (\d+|old)\n\s+Test: divisible by (\d+)\n\s+If true: throw to monkey (\d+)\n\s+If false: throw to monkey (\d+)""")

  timer {
    val monkeys = sections.map { section ->
      println(section)
      val (i, items, operator, operatorArg, divisionTest, trueTo, falseTo) = re.matchEntire(section)!!.destructured
      val op = if (operator == "+") Op.ADD else Op.MUL
      val argType = if (operatorArg == "old") ArgType.OLD else ArgType.NUM
      val arg = Arg(argType, if (argType == ArgType.NUM) operatorArg.toLong() else 0)
      Monkey(
        number = i.toInt(),
        items = items.split(",").map { it.trim().toLong() }.toMutableList(),
        worryFunction = op to arg,
        divisionTest = divisionTest.toLong(),
        throwTo = trueTo.toInt() to falseTo.toInt()
      )
    }

    var round = 1
    while (round < 21) {
      monkeys.indices.forEach { number ->
        val thrown = monkeys[number].inspect()
        thrown.map { (to, items) ->
          monkeys[to].items.addAll(items)
        }
      }
      round++
    }

    monkeys.print()
    val part1 = monkeys.sortedBy { it.count }.takeLast(2).map { it.count }.reduce { acc, monkey ->
      acc * monkey
    }
    println(part1)
    check(part1 == inputs[inputFile]!!.first)
  }
}

fun List<Monkey>.print() = this.forEach {
  println(it)
}
