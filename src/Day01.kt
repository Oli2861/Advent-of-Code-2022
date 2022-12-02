fun main() {

    fun toCalorieList(input: List<String>): MutableList<Int> {
        val list = mutableListOf<Int>()
        var currAmount = 0

        for ((index, str) in input.withIndex()) {
            if (str.matches(Regex("[0-9]+"))) {
                currAmount += Integer.parseInt(str)
                if (index == input.size - 1) list.add(currAmount)
            } else {
                list.add(currAmount)
                currAmount = 0
            }
        }
        return list
    }

    fun part1(input: List<String>): Int = toCalorieList(input).max()

    fun part2(input: List<String>): Int {
        val topThree = toCalorieList(input).sortedDescending().slice(0..2)
        return topThree.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")

    val result1 = part1(testInput)
    println(result1)
    check(result1 == 68442)

    val result2 = part2(testInput)
    println(result2)
    check(result2 == 204837)

    /*
    val input = readInput("Day")
    println(part1(input))
    println(part2(input))
    */
}
