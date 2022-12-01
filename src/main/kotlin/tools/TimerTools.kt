package tools

import kotlin.system.measureNanoTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

inline fun timer(block: () -> Unit) {
  measureNanoTime(block).also {
    it.nanoseconds.humanReadableNanos()
  }
}

fun Duration.humanReadableNanos() {
  println(" ${inWholeMinutes}m ${inWholeSeconds}s ${inWholeMilliseconds}ms ( ${inWholeMicroseconds}µs )")
}
