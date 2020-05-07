package edu.towson.connect4_ai

    fun minimax(depth: Int, inputboard: Board): Board{
        var flag = -1 // 0 is player, 1 is silva -1 is unassigned

        if(inputboard.getCurrentPlayer() == Player.YELLOW){
            flag = 1
        } else if(inputboard.getCurrentPlayer() == Player.RED){
            flag = 0
        }


        var out: Board = Board()
        var inp: Board = Board()



        if( depth == 0 ){ // if it is the board to evaluate, aka lowest tree
            if( flag == 1 ) {
                out.value = evaluate(inputboard) * -1      // get the board value for YELLOW tokens

            } else if( flag == 0) {
                out.value = evaluate(inputboard)           // get the board value for RED tokens, negate it
            }
            return out
        }





        if(flag == 1){  // current is yellow
            out.value = -100000
        } else if( flag == 0 ) {    // current is red
            out.value = 100000
        }

        var boardList = generatePossibleMove(inputboard) // make possible moves list of current player playing

        for (b in boardList){
            if(flag == 1){ // if current player is silva

                b.setCurrentPlayer(Player.RED)
                inp = minimax(depth -1, b)


                if(inp.value > out.value){
                    out.value = inp.value
                    out.grid = b.grid
                }
            } else if(flag == 0) {   // if current player is player

                b.setCurrentPlayer(Player.YELLOW)
                inp = minimax(depth -1, b)

                if(inp.value < out.value){
                    out.value = inp.value
                    out.grid = b.grid
                }

            }
        }


        return out
    }

    fun evaluate(board: Board): Int{

        // negate the current player to evaluate that players tokens
        board.updateCurrentPlayer()

        // get the negated player
        var currentPlayer = board.getCurrentPlayer()

        var currentWinner : Winner = Winner.NONE

        // assign current winner to the current player
        if(currentPlayer == Player.RED){
            currentWinner = Winner.RED
        } else if(currentPlayer == Player.YELLOW){
            currentWinner = Winner.YELLOW
        }

        var value = 0

        if(board.getWinner() == currentWinner){
            System.out.println(board.getWinner())
            value += 10000
            // current player won
        }

        // make a function that evaluates each positions value

        // return the current player as it was before
        board.updateCurrentPlayer()
        return value
    }

    fun generatePossibleMove(inputboard: Board): List<Board> {
        var possibleBoard = mutableListOf<Board>()
        var copyBoard = inputboard.copy()

        // generate a possible board list of the current player
        for(i in 1..7){
            if(copyBoard.fromTopAvailable(i)){
                copyBoard.fromTop(i)
                possibleBoard.add(copyBoard)
                copyBoard = inputboard.copy()
            }

        }

        return possibleBoard
    }


