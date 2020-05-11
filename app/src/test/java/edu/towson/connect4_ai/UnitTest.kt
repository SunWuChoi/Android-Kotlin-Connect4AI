package edu.towson.connect4_ai

import edu.towson.connect4_ai.database.AccountDatabaseRepository
import org.junit.Test

import org.junit.Assert.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    ///////////////////
    // Board testing //
    ///////////////////

    @Test
    fun enum_isCorrect() {
        assertEquals(Player.RED, Player.RED)
        assertEquals(Player.YELLOW, Player.YELLOW)
        assertEquals(Player.EMPTY, Player.EMPTY)
        assertEquals(Winner.NONE, Winner.NONE)
        assertEquals(Winner.TIE, Winner.TIE)
        assertEquals(Winner.YELLOW, Winner.YELLOW)
        assertEquals(Winner.RED, Winner.RED)
    }

    @Test
    fun winner_isCorrect() {
        var board = Board()
        board.fromTop(4)
        board.fromTop(4)
        board.fromTop(4)
        board.fromTop(4)

        lateinit var winner: Winner
        if(board.getCurrentPlayer() == Player.RED){
            winner = Winner.RED
        } else if (board.getCurrentPlayer() == Player.YELLOW) {
            winner = Winner.YELLOW
        }
        assertEquals(board.getWinner(), winner)
    }

    @Test
    fun tie_isCorrect() {
        var board = Board()
        board.fromTop(2);board.fromTop(2);board.fromTop(2)
        board.fromTop(4);board.fromTop(4);board.fromTop(4)
        board.fromTop(6);board.fromTop(6);board.fromTop(6)
        board.updateCurrentPlayer()
        board.fromTop(1);board.fromTop(1);board.fromTop(1)
        board.fromTop(3);board.fromTop(3);board.fromTop(3)
        board.fromTop(5);board.fromTop(5);board.fromTop(5)
        board.fromTop(7);board.fromTop(7);board.fromTop(7)
        board.updateCurrentPlayer()
        board.fromTop(1);board.fromTop(1);board.fromTop(1)
        board.fromTop(3);board.fromTop(3);board.fromTop(3)
        board.fromTop(5);board.fromTop(5);board.fromTop(5)
        board.fromTop(7);board.fromTop(7);board.fromTop(7)
        board.updateCurrentPlayer()
        board.fromTop(2);board.fromTop(2);board.fromTop(2)
        board.fromTop(4);board.fromTop(4);board.fromTop(4)
        board.fromTop(6);board.fromTop(6);board.fromTop(6)

        assertEquals(board.getWinner(), Winner.TIE)
    }

    @Test
    fun none_isCorrect(){
        var board = Board()
        assertEquals(board.getWinner(), Winner.NONE)
    }

    @Test
    fun fromTopAvailable_isCorrect() {
        var board = Board()
        var bool = board.fromTopAvailable(1)
        assertEquals(bool, true)

        board.fromTop(1);board.fromTop(1);board.fromTop(1);board.fromTop(1);board.fromTop(1);board.fromTop(1)
        bool = board.fromTopAvailable(1)
        assertEquals(bool, false)
    }

    @Test
    fun getPlayer_isCorrect() {
        var board = Board()
        assertEquals(board.getCurrentPlayer(), Player.RED)

        board.updateCurrentPlayer()
        assertEquals(board.getCurrentPlayer(), Player.YELLOW)

        board.setCurrentPlayer(Player.EMPTY)
        assertEquals(board.getCurrentPlayer(), Player.EMPTY)
    }

    @Test
    fun reset_isCorrect() {
        var board = Board()

        board.updateCurrentPlayer()
        assertEquals(board.getCurrentPlayer(), Player.YELLOW)


        board.reset()


        assertEquals(board.getCurrentPlayer(), Player.RED)
        val newBoard = Board()
        assertEquals(board.grid, newBoard.grid)
    }

    @Test
    fun resetYellow_isCorrect() {
        var board = Board()

        assertEquals(board.getCurrentPlayer(), Player.RED)

        board.resetYellow()

        assertEquals(board.getCurrentPlayer(), Player.YELLOW)
        val newBoard = Board()
        assertEquals(board.grid, newBoard.grid)
    }

    @Test
    fun getBoard_isCorrect() {
        var board = Board()
        board.fromTop(1)

        assertEquals(board.getBoard() , board.grid)
    }

    @Test
    fun copyGrid_isCorrect() {
        var board = Board()
        board.fromTop(1)

        var newBoard = Board()

        newBoard.grid = board.copyGrid()

        assertEquals(newBoard.grid , board.grid)
    }

    @Test
    fun copy_isCorrect() {
        var board = Board()
        board.fromTop(1)
        board.value = 123
        board.updateCurrentPlayer()

        val newBoard = board.copy()

        assertEquals(newBoard.value, board.value)
        assertEquals(newBoard.grid, board.grid)
        assertEquals(newBoard.getCurrentPlayer(), board.getCurrentPlayer())
        assertEquals(newBoard.getWinner(), board.getWinner())
    }

    @Test
    fun setLocation_isCorrect() {
        var board = Board()
        board.setLocation(5,0) // 5 is the bottom, 0 is the first column
        var newBoard = Board()
        newBoard.fromTop(1) // first column, connect 4 places tokens from the bottom

        assertEquals(newBoard.grid, board.grid)
    }

    /////////////////////
    // Minimax testing //
    /////////////////////

    @Test
    fun evaluate_isCorrect() {
        var board = Board()
        board.fromTop(4)
        board.fromTop(4)
        board.fromTop(4)
        board.fromTop(4)
        board.updateCurrentPlayer()

        assertEquals(10000,evaluate(board))
    }

    @Test
    fun evaluateCurrent_isCorrect() {
        var board = Board()
        board.fromTop(4)
        board.fromTop(4)
        board.fromTop(4)
        board.fromTop(4)

        assertEquals(10000,evaluateCurrent(board))
    }

    @Test
    fun ifCurrentPlayerWon_isCorrect() {
        var board = Board()
        board.fromTop(4)
        board.fromTop(4)
        board.fromTop(4)
        board.fromTop(4)

        assertEquals(true, ifCurrentWon(board))

        board.updateCurrentPlayer()

        assertEquals(false, ifCurrentWon(board))

    }

    @Test
    fun generatePossibleMoves_isCorrect() {
        var board = Board()
        var newBoards = generatePossibleMove(board)

        board.fromTop(4)
        assertEquals(board.grid, newBoards[0].grid)
        board.reset()

        board.fromTop(5)
        assertEquals(board.grid, newBoards[1].grid)
        board.reset()

        board.fromTop(3)
        assertEquals(board.grid, newBoards[2].grid)
        board.reset()

        board.fromTop(6)
        assertEquals(board.grid, newBoards[3].grid)
        board.reset()

        board.fromTop(2)
        assertEquals(board.grid, newBoards[4].grid)
        board.reset()

        board.fromTop(7)
        assertEquals(board.grid, newBoards[5].grid)
        board.reset()

        board.fromTop(1)
        assertEquals(board.grid, newBoards[6].grid)
        board.reset()
    }

}
