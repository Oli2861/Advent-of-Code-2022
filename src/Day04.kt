fun getPairs(lines: List<String>): List<Pair<IntRange, IntRange>> = lines.map { getPairs(it) }

fun getPairs(line: String): Pair<IntRange, IntRange> {
    val numbers: List<Int> = line.split(Regex("[^0-9]+")).map { Integer.parseInt(it) }
    return Pair(IntRange(numbers[0], numbers[1]), IntRange(numbers[2], numbers[3]))
}

fun isFullyContained(pair: Pair<IntRange, IntRange>): Boolean {
    val (first, second) = pair
    val secondFullyContainedInFirst = first.contains(second.first) && first.contains(second.last)
    val firstFullyContainedInSecond = second.contains(first.first) && second.contains(first.last)
    return secondFullyContainedInFirst || firstFullyContainedInSecond
}

fun isOverlapping(pair: Pair<IntRange, IntRange>): Boolean {
    val (first, second) = pair
    val secondOverlapsFirst = first.contains(second.first) || first.contains(second.last)
    val firstOverlapsSecond = second.contains(first.first) || second.contains(first.last)
    return secondOverlapsFirst || firstOverlapsSecond
}

fun amountOfPairsMatchingCondition(lines: List<String>, condition: (Pair<IntRange, IntRange>) -> Boolean): Int =
    getPairs(lines).map { condition(it) }.filter { it }.size

fun amountOfContainedPairs(lines: List<String>): Int = amountOfPairsMatchingCondition(lines, ::isFullyContained)

fun amountOfOverlappingPairs(lines: List<String>): Int = amountOfPairsMatchingCondition(lines, ::isOverlapping)


fun main() {
    val input = readInput("Day04_test")

    val result = amountOfContainedPairs(input)
    println(result)
    check(result == 556)

    val result2 = amountOfOverlappingPairs(input)
    println(result2)
    check(result2 == 876)
}