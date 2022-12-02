enum class Result(val score: Int) {
    WIN(6), Draw(3), Lose(0)
}

enum class Choice(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    companion object {
        val winsAgainstMap = mapOf(
            ROCK to PAPER,
            PAPER to SCISSORS,
            SCISSORS to ROCK
        )
    }

    private val losesTo: Choice
        get() {
            return winsAgainstMap[this]!!
        }

    fun scoreAgainst(other: Choice): Int {
        if (this == other) return Result.Draw.score
        return when (this) {
            ROCK -> if (other == SCISSORS) Result.WIN.score else Result.Lose.score
            PAPER -> if (other == ROCK) Result.WIN.score else Result.Lose.score
            SCISSORS -> if (other == PAPER) Result.WIN.score else Result.Lose.score
        }
    }

    fun getChoiceForDesiredResult(desiredResult: Result): Choice {
        return when (desiredResult) {
            Result.WIN -> this.losesTo
            Result.Draw -> this
            Result.Lose -> this.losesTo.losesTo
        }
    }
}

val charChoiceMap = mapOf(
    'A' to Choice.ROCK,
    'X' to Choice.ROCK,
    'B' to Choice.PAPER,
    'Y' to Choice.PAPER,
    'Z' to Choice.SCISSORS,
    'C' to Choice.SCISSORS
)

val requiredResult = mapOf(
    'Y' to Result.Draw,
    'X' to Result.Lose,
    'Z' to Result.WIN
)
// For fun: task 1 in one line :D (still using maps / enum classes though :o )
// fun getTotalScoresInOneLine(choices: List<String>) = choices.map { Pair(charChoiceMap[it[2]], charChoiceMap[it[0]]) }.sumOf { it.first!!.score + if (it.first == it.second) Result.Draw.score else { if (it.first == Choice.ROCK) { if (it.second == Choice.SCISSORS) Result.WIN.score else Result.Lose.score } else if (it.first == Choice.PAPER) { if (it.second == Choice.ROCK) Result.WIN.score else Result.Lose.score } else { if (it.second == Choice.PAPER) Result.WIN.score else Result.Lose.score } } }

fun getChoices(input: String): Pair<Choice, Choice>? {
    val playerChoice: Choice = charChoiceMap[input[2]] ?: return null
    val opponentChoice: Choice = charChoiceMap[input[0]] ?: return null
    return Pair(playerChoice, opponentChoice)
}

@JvmName("getTotalScores1")
fun getTotalScores(choices: List<String>) = getTotalScores(choices.map { getChoices(it) })

fun getTotalScores(choices: List<Pair<Choice, Choice>?>) = choices.sumOf {
    if (it != null) getScore(it.first, it.second) else 0
}

fun getScore(playerChoice: Choice, opponentChoice: Choice): Int =
    playerChoice.score + playerChoice.scoreAgainst(opponentChoice)
fun makeDecision(character: Char, opponentChoice: Char): Pair<Choice, Choice>? {
    val oppChoice: Choice = charChoiceMap[opponentChoice] ?: return null
    val requiredResult = requiredResult[character] ?: return null
    val playerChoice = oppChoice.getChoiceForDesiredResult(requiredResult)
    return Pair(playerChoice, oppChoice)
}

fun playWithElfInstruction(choices: List<String>): Int {
    val choicesWithDecisions: List<Pair<Choice, Choice>?> = choices.map { makeDecision(it[2], it[0]) }
    return choicesWithDecisions.sumOf { if (it != null) getScore(it.first, it.second) else 0 }
}

fun main() {

    val testInput = readInput("Day02_test")
    val result = getTotalScores(testInput)
    println(result)
    check(result == 14531)

    val result2 = playWithElfInstruction(testInput)
    println(result2)
    check(result2 == 11258)
}