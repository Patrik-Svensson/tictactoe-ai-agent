import java.util.*;

public class Player {
    public static final int MAX_DEPTH = 12;

    /**
     * Performs a move
     *
     * @param gameState
     *            the current state of the board
     * @param deadline
     *            time before which we must have returned
     * @return the next state the board is in after our move
     */
    public GameState play(final GameState gameState, final Deadline deadline) {
        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);

        if (nextStates.size() == 0) {
            // Must play "pass" move if there are no other moves possible.
            return new GameState(gameState, new Move());
        }

        /**
         * Here you should write your algorithms to get the best next move, i.e. the
         * best next state. This skeleton returns a random move instead.
         */
        int maxValue = miniMaxAlgorithm(nextStates.get(0), Constants.CELL_O, MAX_DEPTH, -Integer.MAX_VALUE, Integer.MAX_VALUE);
        GameState maxGameState = nextStates.get(0);
        for (int i = 1; i < nextStates.size(); i++)
        {
            int current = miniMaxAlgorithm(nextStates.get(i), Constants.CELL_O, MAX_DEPTH, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            if(current > maxValue)
            {
                maxValue = current;
                maxGameState = nextStates.get(i);
            }
        }

        return maxGameState;
    }

    public static int miniMaxAlgorithm(GameState state, int player, int depth, int alpha, int beta)
    {
        int bestPossible;
        Vector<GameState> nextStates = new Vector<GameState>();
        state.findPossibleMoves(nextStates);
        int value;

        if(state.isEOG() || state.isOWin() || state.isXWin() || depth <= 0)
        {
            //System.out.println(state.toString(Constants.CELL_X));
            //System.out.println(evaluation(state));
            return evaluation(state);
        }
        else if(player == Constants.CELL_X)
        {
            bestPossible = -Integer.MAX_VALUE;
            for (int i = 0; i < nextStates.size(); i++)
            {
                bestPossible = Math.max(bestPossible, miniMaxAlgorithm(nextStates.get(i), Constants.CELL_O, --depth, alpha, beta));
                alpha = Math.max(bestPossible, alpha);
                if(beta <= alpha)
                    break;
            }
        }
        else
        {
            bestPossible = Integer.MAX_VALUE;
            for (int i = 0; i < nextStates.size(); i++)
            {
                bestPossible = Math.min(bestPossible, miniMaxAlgorithm(nextStates.get(i), Constants.CELL_X, --depth, alpha, beta));
                beta = Math.min(beta, bestPossible);
                if(beta <= alpha)
                    break;
            }
        }

        return bestPossible;
    }

    private static int evaluation(GameState state)
    {
        int numberOfMarks = 0;
        int counter;
        int Ocounter;
        int emptyCounter;

        for (int row = 0; row < state.BOARD_SIZE; row++)
        {
            counter = 0;
            Ocounter= 0;
            emptyCounter = 0;
            for (int col = 0; col < state.BOARD_SIZE; col++)
            {
                if(state.at(row,col) == Constants.CELL_X)
                {
                    counter++;
                }
                else if(state.at(row,col) == Constants.CELL_EMPTY)
                {
                  emptyCounter++;
                }
                else if(state.at(row,col) == Constants.CELL_O)
                {
                  Ocounter++;
                }
            }

            if (counter == 4)
            {
                numberOfMarks += 25;
            }
            else if (counter == 3 && emptyCounter == 1)
            {
                numberOfMarks += 10;
            }
            else if (counter == 2 && emptyCounter == 2)
            {
                numberOfMarks += 3;
            }
            else if (counter == 1 && emptyCounter == 3)
            {
                numberOfMarks++;
            }
            else if (Ocounter == 4)
            {
                numberOfMarks -= 25;
            }
            else if (Ocounter == 3 && emptyCounter == 1)
            {
                numberOfMarks -= 10;
            }
            else if (Ocounter == 2 && emptyCounter == 2)
            {
                numberOfMarks -= 3;
            }
            else if (Ocounter == 1 && emptyCounter == 3)
            {
                numberOfMarks--;
            }
        }

        for (int col = 0; col < state.BOARD_SIZE; col++)
        {
            counter = 0;
            Ocounter = 0;
            emptyCounter = 0;
            for (int row = 0; row < state.BOARD_SIZE; row++)
            {
              if(state.at(row,col) == Constants.CELL_X)
              {
                  counter++;
              }
              else if(state.at(row,col) == Constants.CELL_EMPTY)
              {
                emptyCounter++;
              }
              else if(state.at(row,col) == Constants.CELL_O)
              {
                Ocounter++;
              }
            }

            if (counter == 4)
            {
                numberOfMarks += 25;
            }
            else if (counter == 3 && emptyCounter == 1)
            {
                numberOfMarks += 10;
            }
            else if (counter == 2 && emptyCounter == 2)
            {
                numberOfMarks += 3;
            }
            else if (counter == 1 && emptyCounter == 3)
            {
                numberOfMarks++;
            }
            else if (Ocounter == 4)
            {
                numberOfMarks -= 25;
            }
            else if (Ocounter == 3 && emptyCounter == 1)
            {
                numberOfMarks -= 10;
            }
            else if (Ocounter == 2 && emptyCounter == 2)
            {
                numberOfMarks -= 3;
            }
            else if (Ocounter == 1 && emptyCounter == 3)
            {
                numberOfMarks--;
            }
        }



        //check diagonals
        /*if(state.at(0,0) == Constants.CELL_X) numberOfMarks++;
        if(state.at(1,1) == Constants.CELL_X) numberOfMarks++;
        if(state.at(2,2) == Constants.CELL_X) numberOfMarks++;
        if(state.at(3,3) == Constants.CELL_X) numberOfMarks++;

        if(state.at(3,0) == Constants.CELL_X) numberOfMarks++;
        if(state.at(2,1) == Constants.CELL_X) numberOfMarks++;
        if(state.at(1,2) == Constants.CELL_X) numberOfMarks++;
        if(state.at(0,3) == Constants.CELL_X) numberOfMarks++;*/

        counter = 0;
        Ocounter = 0;
        emptyCounter = 0;
        for (int row = 0; row < state.BOARD_SIZE; row++)
        {
          if(state.at(row, row) == Constants.CELL_X)
          {
              counter++;
          }
          else if(state.at(row,row) == Constants.CELL_EMPTY)
          {
            emptyCounter++;
          }
          else if(state.at(row,row) == Constants.CELL_O)
          {
            Ocounter++;
          }
        }

        if (counter == 4)
        {
            numberOfMarks += 25;
        }
        else if (counter == 3 && emptyCounter == 1)
        {
            numberOfMarks += 10;
        }
        else if (counter == 2 && emptyCounter == 2)
        {
            numberOfMarks += 3;
        }
        else if (counter == 1 && emptyCounter == 3)
        {
            numberOfMarks++;
        }
        else if (Ocounter == 4)
        {
            numberOfMarks -= 25;
        }
        else if (Ocounter == 3 && emptyCounter == 1)
        {
            numberOfMarks -= 10;
        }
        else if (Ocounter == 2 && emptyCounter == 2)
        {
            numberOfMarks -= 3;
        }
        else if (Ocounter == 1 && emptyCounter == 3)
        {
            numberOfMarks--;
        }


        counter = 0;
        Ocounter = 0;
        emptyCounter = 0;
        for (int row = 0; row < state.BOARD_SIZE; row++)
        {
          if(state.at(row, state.BOARD_SIZE - 1 - row) == Constants.CELL_X)
          {
            counter++;
          }
          else if(state.at(row, state.BOARD_SIZE - 1 - row) == Constants.CELL_EMPTY)
          {
            emptyCounter++;
          }
          else if(state.at(row, state.BOARD_SIZE - 1 - row) == Constants.CELL_O)
          {
            Ocounter++;
          }
        }

        if (counter == 4)
        {
            numberOfMarks += 25;
        }
        else if (counter == 3 && emptyCounter == 1)
        {
            numberOfMarks += 10;
        }
        else if (counter == 2 && emptyCounter == 2)
        {
            numberOfMarks += 3;
        }
        else if (counter == 1 && emptyCounter == 3)
        {
            numberOfMarks++;
        }
        else if (Ocounter == 4)
        {
            numberOfMarks -= 25;
        }
        else if (Ocounter == 3 && emptyCounter == 1)
        {
            numberOfMarks -= 10;
        }
        else if (Ocounter == 2 && emptyCounter == 2)
        {
            numberOfMarks -= 3;
        }
        else if (Ocounter == 1 && emptyCounter == 3)
        {
            numberOfMarks--;
        }

        return numberOfMarks;
    }
}
