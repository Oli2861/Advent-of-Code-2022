fun getNumList(input: List<String>) = input.map { line ->
    line.toCharArray().map { character ->
        character.digitToInt()
    }
}

fun getAmountOfVisibleTrees(input: List<String>): Int {

    val numList = getNumList(input)
    val isVisibleList: List<MutableList<Boolean>> = numList.map { it.map { false }.toMutableList() }
    val lastVisibleInColumn: MutableList<Int> = numList.first().toMutableList()
    val lastVisibleFromBottom: MutableList<Int> = numList.last().toMutableList()

    for (rowIndex in numList.indices) {
        var lastVisibleInRow = 0
        var lastVisibleFromBack = 0
        for (columnIndex in numList[rowIndex].indices) {
            // Compare to last visible in row.
            if (lastVisibleInRow < numList[rowIndex][columnIndex] || columnIndex == 0) {
                lastVisibleInRow = numList[rowIndex][columnIndex]
                isVisibleList[rowIndex][columnIndex] = true
            }
            // Compare from back.
            val indexFromBack = numList[rowIndex].size - columnIndex - 1
            if (lastVisibleFromBack < numList[rowIndex][indexFromBack] || indexFromBack == numList[rowIndex].size - 1) {
                lastVisibleFromBack = numList[rowIndex][indexFromBack]
                isVisibleList[rowIndex][indexFromBack] = true
            }
            // Compare to last visible in column.
            if (lastVisibleInColumn[columnIndex] < numList[rowIndex][columnIndex] || rowIndex == 0) {
                lastVisibleInColumn[columnIndex] = numList[rowIndex][columnIndex]
                isVisibleList[rowIndex][columnIndex] = true
            }
            // Compare from bottom.
            val rowIndexFromBottom = numList.size - rowIndex - 1
            if (lastVisibleFromBottom[columnIndex] < numList[rowIndexFromBottom][columnIndex] || rowIndexFromBottom == numList.size - 1) {
                lastVisibleFromBottom[columnIndex] = numList[rowIndexFromBottom][columnIndex]
                isVisibleList[rowIndexFromBottom][columnIndex] = true
            }
        }
    }
    return isVisibleList.map { list -> list.map { if (it) 1 else 0 } }.sumOf { it.sum() }
}


fun main() {
    val test = listOf(
        "30373",
        "25512",
        "65332",
        "33549",
        "35390"
    )
    println(getAmountOfVisibleTrees(test))
    println("-------")

    val input = readInput("Day08_test")

    val result = getAmountOfVisibleTrees(input)
    println(result)
    check(result == 1669)


}