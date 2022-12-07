import kotlin.math.absoluteValue

fun String.getDigits(): Int? {
    return this.filter { it.isDigit() }.toIntOrNull()
}

object CommandConstants {
    const val CD_COMMAND = "$ cd"
    const val DESTINATION_HOME = "/"
    const val DESTINATION_UP = ".."
}

fun evaluateLog(lines: List<String>): MutableMap<String, Long> {
    val dirMap = HashMap<String, Long>()
    val currentPathDirectories = ArrayDeque<String>()

    for (line in lines) {
        if (line.contains(CommandConstants.CD_COMMAND)) {
            val destination = line.subSequence(
                line.indexOf(CommandConstants.CD_COMMAND) + CommandConstants.CD_COMMAND.length,
                line.length
            ).filter { !it.isWhitespace() }.toString()

            when (destination) {
                CommandConstants.DESTINATION_HOME -> {
                    currentPathDirectories.removeAll(currentPathDirectories)
                    currentPathDirectories.add(CommandConstants.DESTINATION_HOME)
                }

                CommandConstants.DESTINATION_UP -> currentPathDirectories.removeLast()

                else -> currentPathDirectories.add(destination)
            }

        } else if (line[0].isDigit()) {
            val value = line.getDigits()
            for (index in currentPathDirectories.indices) {
                val path = currentPathDirectories.subList(0, index + 1).joinToString(",")
                dirMap[path] = (dirMap[path] ?: 0) + (value ?: 0)
            }
        }
    }
    return dirMap
}

fun getDirectoriesSmallerThan(commands: List<String>, value: Int = 100000) =
    evaluateLog(commands).filter { it.value <= value }.values.sum()

fun getSizeOfDirectoryToBeDeleted(updateSize: Int, totalSpace: Int, commands: List<String>): Long {
    val directories = evaluateLog(commands)
    val freeSpace = totalSpace - directories[CommandConstants.DESTINATION_HOME]!!
    val requiredSpace = (freeSpace - updateSize).absoluteValue

    var searchedDir: MutableMap.MutableEntry<String, Long>? = null
    var minDiff = directories.values.max()

    for (directory in directories) {
        val diff = directory.value - requiredSpace
        if (diff in 0..minDiff) {
            minDiff = directory.value - requiredSpace
            searchedDir = directory
        }
    }
    return searchedDir?.value ?: 0
}

fun main() {
    val input = readInput("Day07_test")
    val result = getDirectoriesSmallerThan(input)
    println(result)
    check(result == 1086293L)

    val results2 = getSizeOfDirectoryToBeDeleted(30000000, 70000000, input)
    println(results2)
    check(results2 == 366028L)
}