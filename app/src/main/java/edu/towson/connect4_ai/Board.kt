package edu.towson.connect4_ai


class Board : IBoard {

    private var currentPlayer = Player.X
    private var winner = Player.EMPTY
    lateinit var grid: List<MutableList<Player>>

    init {
        reset()
    }

    override fun getWinner(): Winner {
        winner = when(hasWinner()) {
            true -> currentPlayer
            false -> Player.EMPTY
        }
        return when(winner) {
            Player.X -> Winner.X
            Player.O -> Winner.O
            Player.EMPTY -> {
                if(boardFull()) {
                    Winner.TIE
                } else {
                    Winner.NONE
                }
            }
        }
    }

    private fun boardFull(): Boolean {
        return grid.all {
            it.none { it == Player.EMPTY }
        }
    }

    private fun hasWinner(): Boolean {
        return (0..2).any { columnWinner(it) }
                || (0..2).any { rowWinner(it) }
                || leftDiagWinner()
                || rightDiagWinner()
    }

    private fun columnWinner(col: Int): Boolean {
        return grid.all { it[col] == currentPlayer }
    }

    private fun rowWinner(row: Int): Boolean {
        return grid[row].all { it == currentPlayer }
    }

    private fun leftDiagWinner(): Boolean {
        var col = 0
        return grid.all {
            it[col++] == currentPlayer
        }
    }

    private fun rightDiagWinner(): Boolean {
        var col = 2
        return grid.all {
            it[col--] == currentPlayer
        }
    }

    override fun setLocation(x: Int, y: Int): Boolean {
        if(winner != Player.EMPTY) return false
        if(x < 3 && x >= 0) {
            if(y < 3 && y >=0) {
                if(grid[x][y] != Player.EMPTY) {
                    return false
                }
                grid[x][y] = currentPlayer
                return true
            }
        }
        return false
    }

    override fun updateCurrentPlayer() {
        currentPlayer = when(currentPlayer) {
            Player.EMPTY -> throw Exception("Why is current player EMPTY?")
            Player.X -> Player.O
            Player.O -> Player.X
        }
    }

    override fun getBoard(): List<List<Player>> {
        return grid
    }

    override fun getCurrentPlayer(): Player {
        return currentPlayer
    }

    override fun reset() {
        grid = (1..3).map {
            (1..3).map { Player.EMPTY }.toMutableList()
        }
        winner = Player.EMPTY
        currentPlayer = Player.X
    }

    enum class Player { X, O, EMPTY }
    enum class Winner { X, O, TIE, NONE }
}

interface IBoard {
    fun getWinner(): Board.Winner
    fun setLocation(x: Int, y: Int): Boolean
    fun updateCurrentPlayer()
    fun getCurrentPlayer(): Board.Player
    fun getBoard(): List<List<Board.Player>>
    fun reset()
}