package edu.towson.connect4_ai

interface IController {
    fun updateCell(rowNum: Int, colNum: Int, cell: Board.Player)
    fun updateViewWithBoard()
    fun updateWinView()
    fun handleWin()
    fun resetView()
}

interface IBoard {
    fun getWinner(): Board.Winner
    fun setLocation(x: Int, y: Int): Boolean
    fun updateCurrentPlayer()
    fun getCurrentPlayer(): Board.Player
    fun getBoard(): List<List<Board.Player>>
    fun reset()
}