package edu.towson.connect4_ai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, IController {

    // a reference to our model
    lateinit var board: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupGridLayout()

        // our model
        board = Board()

        // initialize the view with the model
        updateViewWithBoard()
    }

    override fun onClick(v: View?) {
        // set the board location with the current player
        // returns true if the move is legal
        val success = when(v) {
            c00 -> board.setLocation(0,0)
            c01 -> board.setLocation(0,1)
            c02 -> board.setLocation(0,2)
            c03 -> board.setLocation(0,3)
            c04 -> board.setLocation(0,4)
            c05 -> board.setLocation(0,5)
            c06 -> board.setLocation(0,6)

            c10 -> board.setLocation(1,0)
            c11 -> board.setLocation(1,1)
            c12 -> board.setLocation(1,2)
            c13 -> board.setLocation(1,3)
            c14 -> board.setLocation(1,4)
            c15 -> board.setLocation(1,5)
            c16 -> board.setLocation(1,6)

            c20 -> board.setLocation(2,0)
            c21 -> board.setLocation(2,1)
            c22 -> board.setLocation(2,2)
            c23 -> board.setLocation(2,3)
            c24 -> board.setLocation(2,4)
            c25 -> board.setLocation(2,5)
            c26 -> board.setLocation(2,6)

            c30 -> board.setLocation(3,0)
            c31 -> board.setLocation(3,1)
            c32 -> board.setLocation(3,2)
            c33 -> board.setLocation(3,3)
            c34 -> board.setLocation(3,4)
            c35 -> board.setLocation(3,5)
            c36 -> board.setLocation(3,6)

            c40 -> board.setLocation(4,0)
            c41 -> board.setLocation(4,1)
            c42 -> board.setLocation(4,2)
            c43 -> board.setLocation(4,3)
            c44 -> board.setLocation(4,4)
            c45 -> board.setLocation(4,5)
            c46 -> board.setLocation(4,6)

            c50 -> board.setLocation(5,0)
            c51 -> board.setLocation(5,1)
            c52 -> board.setLocation(5,2)
            c53 -> board.setLocation(5,3)
            c54 -> board.setLocation(5,4)
            c55 -> board.setLocation(5,5)
            c56 -> board.setLocation(5,6)

            resetBtn -> {
                // if the reset button was clicked, reset the board and view
                board.reset()
                resetView()
                false
            }
            else -> false
        }

        if(success) {
            // check for a win and update the view accordingly
            handleWin()
            updateViewWithBoard()
        } else {
            updateViewWithBoard()
        }
    }

    override fun resetView() {
        currentPlayerTextView.visibility = View.VISIBLE
        resetBtn.visibility = View.GONE
        winnerTextView.text = ""
    }

    override fun handleWin() {
        when(board.getWinner()) {
            Board.Winner.X -> {
                winnerTextView.text = getString(R.string.x_wins)
                updateWinView()
            }
            Board.Winner.O -> {
                winnerTextView.text = getString(R.string.o_wins)
                updateWinView()
            }
            Board.Winner.NONE -> {
                // if no one has won, update the current player
                board.updateCurrentPlayer()
            }
            Board.Winner.TIE -> {
                winnerTextView.text = getString(R.string.tie_text)
                updateWinView()
            }
        }
    }

    override fun updateWinView() {
        winnerTextView.visibility = View.VISIBLE
        resetBtn.visibility = View.VISIBLE
        currentPlayerTextView.visibility = View.GONE
    }

    /**
     * Helper function to set click listeners on all the grid items and reset button
     */
    private fun setupGridLayout() {
        val cnt = boardView.childCount
        (0..cnt).forEach {
            val view: View? = boardView.getChildAt(it)
            view?.setOnClickListener(this)
        }
        resetBtn.setOnClickListener(this)
    }

    /**
     * Updates the grid based on the model.
     * Updates the current player text
     */
    override fun updateViewWithBoard() {
        board.getBoard()
            .forEachIndexed { rowNum, row ->
                row.forEachIndexed { colNum, cell  ->
                    updateCell(rowNum, colNum, cell)
                }
            }
        currentPlayerTextView.text = board.getCurrentPlayer().name
    }

    /**
     * Updates the cell at rowNum,colNum with the given player
     */
    override fun updateCell(rowNum: Int, colNum: Int, cell: Board.Player) {
        val cnt = boardView.childCount
        (0..cnt).forEach {
            val rowCol = colNum + 7 * rowNum
            if(rowCol == it) {
                val tv = boardView.getChildAt(it) as TextView?
                when (cell) {
                    Board.Player.X -> tv?.text = "X"
                    Board.Player.O -> tv?.text = "O"
                    Board.Player.EMPTY -> tv?.text = ""
                }
            }
        }
    }
}

interface IController {
    fun updateCell(rowNum: Int, colNum: Int, cell: Board.Player)
    fun updateViewWithBoard()
    fun updateWinView()
    fun handleWin()
    fun resetView()
}
