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
        var count = 0
        var height = 5
        var width = 6
        //vertical win check
        for(y in 0 .. 6){
            for(x in 0 .. 5){
                if(grid[x][y] == currentPlayer){
                    count++

                    if(count >= 4){
                        return true
                    }
                }else{
                    count = 0
                }
            }
        }

        //horizontal win check
        count = 0
        for(x in 0 .. 5){
            for(y in 0 .. 6){
                if(grid[x][y] == currentPlayer){
                    count++

                    if(count >= 4){
                        return true
                    }
                }else{
                    count = 0
                }
            }
        }
/*
        //diagonal top right to bottom left upper quarter
        count = 0
        for(y in 3..5){
            var newx = 0
            var newy = y
            while(newy >= 0) {
                if (grid[newx][newy] == currentPlayer) {
                    count++
                    if (count >= 4) {
                        return true
                    }
                    newx++
                    newy--

                } else {
                    count = 0
                }
            }
        }

        //diagonal top right to bottom left lower quarter
        count = 0
        for(y in 1..3){
            var newy = y
            var newx = height
            while(newy <= 6) {
                if (grid[newx][newy] == currentPlayer) {
                    count++
                    if (count >= 4) {
                        return true
                    }
                    newx--
                    newy++

                } else {
                    count = 0
                }
            }
        }

        //diagonal top left to bottom right upper quarter
        count = 0
        for(y in 1..3){
            var newy = y
            var newx = 0
            while(newy <= 6) {
                if (grid[newx][newy] == currentPlayer) {
                    count++
                    if (count >= 4) {
                        return true
                    }
                    newx++
                    newy++

                } else {
                    count = 0
                }
            }
        }

        //diagonal top left to bottom right lower quarter
        count = 0
        for(y in 3..5){
            var newy = y
            var newx = 5
            while(newy >= 0) {
                if (grid[newx][newy] == currentPlayer) {
                    count++
                    if (count >= 4) {
                        return true
                    }
                    newx--
                    newy--

                } else {
                    count = 0
                }
            }
        }
*/
        return false
    }

    override fun setLocation(x: Int, y: Int): Boolean {
        if(winner != Player.EMPTY) return false
        if(x in 0..5) {
            if(y in 0..6) {
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
        grid = (0..5).map {
            (0..6).map { Player.EMPTY }.toMutableList()
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