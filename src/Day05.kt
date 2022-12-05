fun prepareInput(input: List<String>): Pair<List<Triple<Int, Int, Int>>, MutableList<ArrayDeque<Char>>> {
    val splitIndex = input.indexOfFirst { it.isEmpty() } - 1

    val instructionString = input.subList(splitIndex + 2, input.size)
    val instructions: List<Triple<Int, Int, Int>> = toInstructions(instructionString)

    val storage: List<String> = input.subList(0, splitIndex)
    val stackList = toStackList(storage)
    return Pair(instructions, stackList)
}

fun toStackList(storage: List<String>): MutableList<ArrayDeque<Char>> {

    val stackList: MutableList<ArrayDeque<Char>> = MutableList(9) { ArrayDeque<Char>() }

    for (storageRow in storage) {
        for ((stackNum, index) in (1 until storageRow.length step 4).withIndex()) {
            val curr = storageRow[index]
            if (curr.isLetter()) stackList[stackNum].add(curr)
        }
    }

    return stackList
}

fun toInstructions(input: List<String>): List<Triple<Int, Int, Int>> {
    return input.map { row ->
        val onlyDigits = row.filter { it.isDigit() || it == ' ' }.split(' ').filter { it.matches(Regex("[0-9]+")) }
        return@map Triple(
            onlyDigits[0].toInt(), onlyDigits[1].toInt(), onlyDigits[2].toInt()
        )
    }
}

fun simulateCrateMovement(input: List<String>, canMoveMultiple: Boolean = false): String {
    val (instructions, stackList) = prepareInput(input)

    for ((index, instruction) in instructions.withIndex()) {
        val (amount, startIndex, destinationIndex) = instruction
        val startStack = stackList[startIndex - 1]
        val destinationStack = stackList[destinationIndex - 1]

        if (canMoveMultiple) {
            val payload = startStack.slice(0 until amount)
            for (i in 0 until amount) {
                startStack.removeFirst()
            }
            destinationStack.addAll(0, payload)
        } else {
            for (i in 0 until amount) {
                val payload = startStack.removeFirst()
                destinationStack.addFirst(payload)
            }
        }
    }
    val firstEntries = mutableListOf<Char>()
    stackList.forEach { firstEntries.add(it.first()) }
    return firstEntries.joinToString("")
}


fun main() {
    val input = readInput("Day05_test")
    val result = simulateCrateMovement(input)
    println(result)
    check(result == "BSDMQFLSP")

    val result2 = simulateCrateMovement(input, true)
    println(result2)
    check(result2 == "PGSQBFLDP")
}