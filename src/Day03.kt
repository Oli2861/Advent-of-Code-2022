fun toCompartments(rucksack: String): Pair<String, String> {
    val length = rucksack.length
    val middle = length / 2
    return Pair(rucksack.substring(0, middle), rucksack.substring(middle, length))
}

fun getPriority(char: Char) =
    if (char.isUpperCase()) char.lowercaseChar().code - 64 - 6 else char.uppercaseChar().code - 64

fun findMatches(compartments: Pair<String, String>) = findMatches(compartments.first, compartments.second)

fun findMatches(compartment: String, compartment1: String): List<Char> =
    compartment.toCharArray().toList().filter { compartment1.contains(it) }

fun getMaxPrioMatch(rucksack: String): Int {
    val compartments = toCompartments(rucksack)
    return findMatches(compartments).maxOf { getPriority(it) }
}

fun getMaxPrioMatchSum(rucksacks: List<String>): Int = rucksacks.sumOf { getMaxPrioMatch(it) }

fun getBatch(rucksack: String, rucksack1: String, rucksack2: String): Char =
    rucksack.toCharArray().toList().first { rucksack1.contains(it) && rucksack2.contains(it) }

fun getBatchSum(rucksacks: List<String>): Int {
    var sum = 0
    for (index in 2..rucksacks.size step 3) {
        sum += getPriority(getBatch(rucksacks[index - 2], rucksacks[index - 1], rucksacks[index]))
    }
    return sum
}

fun main() {
    val input = readInput("Day03_text")

    val result = getMaxPrioMatchSum(input)
    println(result)
    check(result == 7889)

    val result2 = getBatchSum(input)
    println(result2)
    check(result2 == 2825)
}