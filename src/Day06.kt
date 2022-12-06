fun findMarker(signal: String, signalLength: Int = 4): Int {

    for (characterIndex in signalLength until signal.length) {
        val set = mutableSetOf<Char>()
        for (backwardsIndex in 0 until signalLength) {
            set.add(signal[characterIndex - backwardsIndex])
        }
        if (set.size == signalLength) return characterIndex + 1
    }
    return -1
}

fun main() {
    val input = readInput("Day06_text")
    val result = findMarker(input.first())
    println(result)
    check(result == 1093)

    val result2 = findMarker(input.first(), 14)
    println(result2)
    check(result2 == 3534)
}