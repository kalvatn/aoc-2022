import Hand.PAPER
import Hand.ROCK
import Hand.SCISSORS
import tools.getResourceAsText

enum class Hand(
  val chars: String,
  val score: Int
) {
  ROCK(chars = "AX", score = 1),
  PAPER(chars = "BY", score = 2),
  SCISSORS(chars = "CZ", score = 3);

  fun isDraw(other: Hand) = other == this
  fun isLoss(other: Hand) = this.losesAgainst() == other
  fun isWin(other: Hand) = this.winsAgainst() == other

  fun losesAgainst() = when (this) {
    ROCK     -> PAPER
    PAPER    -> SCISSORS
    SCISSORS -> ROCK
  }

  fun winsAgainst() = when (this) {
    ROCK     -> SCISSORS
    PAPER    -> ROCK
    SCISSORS -> PAPER
  }

  companion object {
    fun fromString(s: String) = Hand.values().first { it.chars.any { c -> s.contains(c) } }
  }

}

fun main() {
  val input = getResourceAsText("02.txt")

  val matchups = input.lines().map {
    val (opponent, response) = it.split(" ")
    Pair(Hand.fromString(opponent), Hand.fromString(response))
  }

  val win = 6
  val draw = 3
  val loss = 0

  val part1 = matchups.fold(0) { score, (opponent, response) ->
    when {
      response.isWin(opponent)  -> score + response.score + win
      response.isDraw(opponent) -> score + response.score + draw
      response.isLoss(opponent) -> score + response.score + loss
      else                      -> score
    }
  }
  println(part1)

  val part2 = matchups.fold(0) { score, (opponent, response) ->
    when (response) {
      SCISSORS -> score + opponent.losesAgainst().score + win
      PAPER    -> score + opponent.score + draw
      ROCK     -> score + opponent.winsAgainst().score + loss
    }
  }
  println(part2)

}
