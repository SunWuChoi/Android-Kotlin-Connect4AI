package edu.towson.connect4_ai

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.View
import android.widget.TextView
import androidx.core.text.toSpannable
import kotlinx.android.synthetic.main.activity_main.*

class TwoPlayerModeActivity : AppCompatActivity(), View.OnClickListener, IController {

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

            c00 -> board.fromTop(1)
            c01 -> board.fromTop(2)
            c02 -> board.fromTop(3)
            c03 -> board.fromTop(4)
            c04 -> board.fromTop(5)
            c05 -> board.fromTop(6)
            c06 -> board.fromTop(7)

            c10 -> board.fromTop(1)
            c11 -> board.fromTop(2)
            c12 -> board.fromTop(3)
            c13 -> board.fromTop(4)
            c14 -> board.fromTop(5)
            c15 -> board.fromTop(6)
            c16 -> board.fromTop(7)

            c20 -> board.fromTop(1)
            c21 -> board.fromTop(2)
            c22 -> board.fromTop(3)
            c23 -> board.fromTop(4)
            c24 -> board.fromTop(5)
            c25 -> board.fromTop(6)
            c26 -> board.fromTop(7)

            c30 -> board.fromTop(1)
            c31 -> board.fromTop(2)
            c32 -> board.fromTop(3)
            c33 -> board.fromTop(4)
            c34 -> board.fromTop(5)
            c35 -> board.fromTop(6)
            c36 -> board.fromTop(7)

            c40 -> board.fromTop(1)
            c41 -> board.fromTop(2)
            c42 -> board.fromTop(3)
            c43 -> board.fromTop(4)
            c44 -> board.fromTop(5)
            c45 -> board.fromTop(6)
            c46 -> board.fromTop(7)

            c50 -> board.fromTop(1)
            c51 -> board.fromTop(2)
            c52 -> board.fromTop(3)
            c53 -> board.fromTop(4)
            c54 -> board.fromTop(5)
            c55 -> board.fromTop(6)
            c56 -> board.fromTop(7)

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
        currentPlayerTextView.text = when(board.getCurrentPlayer().name){
            "X" -> "Red's turn"
            else -> "Yellow's turn"
        }
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
                    Board.Player.X -> {
                        tv?.setBackgroundResource(R.drawable.red_circle)
                    }
                    Board.Player.O -> {
                        tv?.setBackgroundResource(R.drawable.yellow_circle)
                    }
                    Board.Player.EMPTY -> tv?.setBackgroundResource(R.drawable.circle)
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
