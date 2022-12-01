package tools

fun getResourceAsText(path: String, print: Boolean = false): String =
  object {}.javaClass.getResource("/$path")!!.readText().also {
    if (print) {
      println("$path ( lines: ${it.lines().size}, length: ${it.length}, bytes: ${it.toByteArray().size} ) <<EOF")
      println(it)
      println("EOF")
    }
  }

fun String.printLines() {
  this.lines().forEach { println(it) }
}
