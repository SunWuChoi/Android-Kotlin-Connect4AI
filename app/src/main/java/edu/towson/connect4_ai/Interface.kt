package edu.towson.connect4_ai

interface IController {
    fun updateCell(rowNum: Int, colNum: Int, cell: Player)
    fun updateViewWithBoard()
    fun updateWinView()
    fun handleWin()
    fun resetView()
}

interface IBoard {
    fun getWinner(): Winner
    fun setLocation(x: Int, y: Int): Boolean
    fun updateCurrentPlayer()
    fun getCurrentPlayer(): Player
    fun setCurrentPlayer(player: Player)
    fun getBoard(): List<List<Player>>
    fun reset()
//<<<<<<< HEAD
}
//=======
//    fun resetYellow()
//    fun copy(): Board

//
