package edu.towson.connect4_ai

//https://medium.com/@andresmariscal/unbeatable-tic-tac-toe-an-android-app-using-kotlin-4f4d2576e6fd
/*
fun minimax(depth: Int, player: Player, grid: List<MutableList<Player>>): Array<Int> {
    val nextMoves: MutableList<Array<Int>> = generateMoves()

    var bestScore = when (Player.RED) {
        player -> Int.MIN_VALUE
        else -> Int.MAX_VALUE
    }
    var currentScore: Int
    var bestRow = -1
    var bestCol = -1

    if(nextMoves.isEmpty() || depth == 0){
        bestScore = evaluate()
    } else {
        nextMoves.forEach {
            grid[it[0]][it[1]] = player

            if (player == Player.RED){
                currentScore = minimax(depth - 1, Player.YELLOW)[0]
                if(currentScore > bestScore) {
                    bestScore = currentScore
                    bestRow = it[0]
                    bestCol = it[1]
                }
            } else {
                currentScore = minimax(depth - 1, Player.RED)[0]
                if(currentScore < bestScore) {
                    bestScore = currentScore
                    bestRow = it[0]
                    bestCol = it[1]
                }
            }
            cells[it[0]][it[1]]?.content = Player.EMPTY
        }
    }
    return arrayOf(bestScore, bestRow, bestCol)
}

fun generateMoves(): MutableList<Array<Int>> {

}

fun evaluate() : Int{

}

 */