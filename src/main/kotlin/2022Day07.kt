import tools.getResourceAsText
import tools.timer


enum class FileType {
  DIR,
  FILE
}

data class FileNode(
  val name: String,
  val type: FileType,
  val size: Long = 0,
  val parent: FileNode?,
  val children: MutableList<FileNode> = mutableListOf()
) {

  fun size(): Long = size + children.sumOf { it.size() }

  override fun toString(): String {
    return "$name $type size=${size()}"
  }
}

fun main() = timer {
  val input = getResourceAsText("07.txt")

  val root = FileNode(name = "/", type = FileType.DIR, parent = null)

  var pwd = root
  val dirs = mutableListOf<FileNode>()
  timer {
    dirs.add(pwd)
    val lines = input.lines()
      .iterator()
    while (lines.hasNext()) {
      val line = lines.next()
      val parts = line.split(" ")
      when {
        line.startsWith("$ cd") -> {
          pwd = when (val name = parts.last()
            .trim()) {
            "/"  -> root
            ".." -> pwd.parent!!
            else -> {
              val node = FileNode(name = name, type = FileType.DIR, parent = pwd)
              dirs.add(node)
              pwd.children.add(node)
              node
            }
          }
        }

        line.startsWith("$ ls") -> {}
        else                    -> {
          val name = parts.last()
            .trim()
          val type = if (line.startsWith("dir")) FileType.DIR else FileType.FILE
          val size = if (type == FileType.DIR) 0 else parts.first()
            .toLong()
          val node = FileNode(name, type, size, pwd)
          pwd.children.add(node)
        }
      }
    }

    val part1 = dirs.filter { it.size() <= 100000 }
      .sumOf { it.size() }
    println()

    println(part1)
  }

  timer {
    val used = root.size()
    val max = 70000000
    val minFree = 30000000
    val unused = max - used
    val toFree = minFree - unused
    val part2 = dirs.filter { it.type == FileType.DIR }
      .map { it.size() }
      .filter { it >= toFree }
      .minOf { it }

    println(part2)
  }
}
