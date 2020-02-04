import java.util.*;

public class Player {
    public static final int MAX_DEPTH = 0;

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
        int throughCentreCounter = 0;
        int throughCentreEmptyCounter = 0;
        int throughCentreOCounter = 0;

        for(int depth = 0; depth < state.BOARD_SIZE; depth++)
        {
            for (int row = 0; row < state.BOARD_SIZE; row++)
            {
                counter = 0;
                Ocounter= 0;
                emptyCounter = 0;
                throughCentreCounter = 0;
                throughCentreEmptyCounter = 0;
                throughCentreOCounter = 0;

                for (int col = 0; col < state.BOARD_SIZE; col++)
                {
                    if(state.at(row, col, depth) == Constants.CELL_X)
                    {
                        counter++;
                        if((col == 1 || col == 2) && (row == 1 || row == 2) && (depth == 1 || depth == 2))
                            throughCentreCounter++;
                    }
                    else if(state.at(row, col, depth) == Constants.CELL_EMPTY)
                    {
                      emptyCounter++;
                        if((col == 1 || col == 2) && (row == 1 || row == 2) && (depth == 1 || depth == 2))
                          throughCentreEmptyCounter++;
                    }
                    else if(state.at(row, col, depth) == Constants.CELL_O)
                    {
                      Ocounter++;
                        if((col == 1 || col == 2) && (row == 1 || row == 2) && (depth == 1 || depth == 2))
                          throughCentreOCounter++;
                    }
                }

                numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);
            }

            for (int col = 0; col < state.BOARD_SIZE; col++)
            {
                counter = 0;
                Ocounter = 0;
                emptyCounter = 0;
                throughCentreCounter = 0;
                throughCentreEmptyCounter = 0;
                throughCentreOCounter = 0;
                for (int row = 0; row < state.BOARD_SIZE; row++)
                {
                  if(state.at(row, col, depth) == Constants.CELL_X)
                  {
                      counter++;
                      if((col == 1 || col == 2) && (row == 1 || row == 2) && (depth == 1 || depth == 2))
                          throughCentreCounter++;
                  }
                  else if(state.at(row, col, depth) == Constants.CELL_EMPTY)
                  {
                    emptyCounter++;
                      if((col == 1 || col == 2) && (row == 1 || row == 2) && (depth == 1 || depth == 2))
                        throughCentreEmptyCounter++;
                  }
                  else if(state.at(row, col, depth) == Constants.CELL_O)
                  {
                    Ocounter++;
                      if((col == 1 || col == 2) && (row == 1 || row == 2) && (depth == 1 || depth == 2))
                        throughCentreOCounter++;
                  }
                }

                numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);
            }

            counter = 0;
            Ocounter = 0;
            emptyCounter = 0;
            throughCentreCounter = 0;
            throughCentreEmptyCounter = 0;
            throughCentreOCounter = 0;
            for (int row = 0; row < state.BOARD_SIZE; row++)
            {
              if(state.at(row, row, depth) == Constants.CELL_X)
              {
                  counter++;
                  if((row == 1 || row == 2) && (depth == 1 || depth == 2))
                      throughCentreCounter++;
              }
              else if(state.at(row, row, depth) == Constants.CELL_EMPTY)
              {
                emptyCounter++;
                  if((row == 1 || row == 2) && (depth == 1 || depth == 2))
                    throughCentreEmptyCounter++;
              }
              else if(state.at(row, row, depth) == Constants.CELL_O)
              {
                Ocounter++;
                  if((row == 1 || row == 2) && (depth == 1 || depth == 2))
                    throughCentreOCounter++;
              }
            }

            numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);

            counter = 0;
            Ocounter = 0;
            emptyCounter = 0;
            throughCentreCounter = 0;
            throughCentreEmptyCounter = 0;
            throughCentreOCounter = 0;
            for (int row = 0; row < state.BOARD_SIZE; row++)
            {
              if(state.at(row, state.BOARD_SIZE - 1 - row, depth) == Constants.CELL_X)
              {
                counter++;
                if((row == 1 || row == 2) && (depth == 1 || depth == 2))
                    throughCentreCounter++;
              }
              else if(state.at(row, state.BOARD_SIZE - 1 - row, depth) == Constants.CELL_EMPTY)
              {
                emptyCounter++;
                if((row == 1 || row == 2) && (depth == 1 || depth == 2))
                    throughCentreEmptyCounter++;
              }
              else if(state.at(row, state.BOARD_SIZE - 1 - row, depth) == Constants.CELL_O)
              {
                Ocounter++;
                if((row == 1 || row == 2) && (depth == 1 || depth == 2))
                    throughCentreOCounter++;
              }
            }

            numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);
        }

        for (int i = 0; i < state.BOARD_SIZE; i++)
        {
            for(int j = 0; j < state.BOARD_SIZE; j++)
            {
                counter = 0;
                Ocounter = 0;
                emptyCounter = 0;
                throughCentreCounter = 0;
                throughCentreEmptyCounter = 0;
                throughCentreOCounter = 0;
                for (int k = 0; k < state.BOARD_SIZE; k++)
                {
                    if(state.at(i, j, k) == Constants.CELL_X)
                    {
                      counter++;
                      if((i == 1 || i == 2) && (j == 1 || j == 2) && (k == 1 || k == 2))
                          throughCentreCounter++;
                    }
                    else if(state.at(i, j, k) == Constants.CELL_EMPTY)
                    {
                      emptyCounter++;
                      if((i == 1 || i == 2) && (j == 1 || j == 2) && (k == 1 || k == 2))
                          throughCentreEmptyCounter++;
                    }
                    else if(state.at(i, j, k) == Constants.CELL_O)
                    {
                      Ocounter++;
                      if((i == 1 || i == 2) && (j == 1 || j == 2) && (k == 1 || k == 2))
                          throughCentreOCounter++;
                    }
                }
                numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);
            }
        }

        counter = 0;
        Ocounter = 0;
        emptyCounter = 0;
        throughCentreCounter = 0;
        throughCentreEmptyCounter = 0;
        throughCentreOCounter = 0;
        for(int i = 0; i < state.BOARD_SIZE; i++)
        {
            if(state.at(i, i, i) == Constants.CELL_X)
            {
              counter++;
              if(i == 1 || i == 2)
                  throughCentreCounter++;
            }
            else if(state.at(i, i, i) == Constants.CELL_EMPTY)
            {
              emptyCounter++;
              if(i == 1 || i == 2)
                  throughCentreEmptyCounter++;
            }
            else if(state.at(i, i, i) == Constants.CELL_O)
            {
              Ocounter++;
              if(i == 1 || i == 2)
                  throughCentreOCounter++;
            }
        }
        numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);

        counter = 0;
        Ocounter = 0;
        emptyCounter = 0;
        throughCentreCounter = 0;
        throughCentreEmptyCounter = 0;
        throughCentreOCounter = 0;
        for(int i = 0; i < state.BOARD_SIZE; i++)
        {
            if(state.at(i, state.BOARD_SIZE - 1 - i, i) == Constants.CELL_X)
            {
              counter++;
              if(i == 1 || i == 2)
                  throughCentreCounter++;
            }
            else if(state.at(i, state.BOARD_SIZE - 1 - i, i) == Constants.CELL_EMPTY)
            {
              emptyCounter++;
              if(i == 1 || i == 2)
                  throughCentreEmptyCounter++;
            }
            else if(state.at(i, state.BOARD_SIZE - 1 - i, i) == Constants.CELL_O)
            {
              Ocounter++;
              if(i == 1 || i == 2)
                  throughCentreOCounter++;
            }
        }
        numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);

        counter = 0;
        Ocounter = 0;
        emptyCounter = 0;
        throughCentreCounter = 0;
        throughCentreEmptyCounter = 0;
        throughCentreOCounter = 0;
        for(int i = 0; i < state.BOARD_SIZE; i++)
        {
            if(state.at(i, i, state.BOARD_SIZE - 1 - i) == Constants.CELL_X)
            {
              counter++;
              if(i == 1 || i == 2)
                  throughCentreCounter++;
            }
            else if(state.at(i, i, state.BOARD_SIZE - 1 - i) == Constants.CELL_EMPTY)
            {
              emptyCounter++;
              if(i == 1 || i == 2)
                  throughCentreEmptyCounter++;
            }
            else if(state.at(i, i, state.BOARD_SIZE - 1 - i) == Constants.CELL_O)
            {
              Ocounter++;
              if(i == 1 || i == 2)
                  throughCentreOCounter++;
            }
        }
        numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);

        counter = 0;
        Ocounter = 0;
        emptyCounter = 0;
        throughCentreCounter = 0;
        throughCentreEmptyCounter = 0;
        throughCentreOCounter = 0;
        for(int i = 0; i < state.BOARD_SIZE; i++)
        {
            if(state.at(state.BOARD_SIZE - 1 - i, i, i) == Constants.CELL_X)
            {
              counter++;
              if(i == 1 || i == 2)
                  throughCentreCounter++;
            }
            else if(state.at(state.BOARD_SIZE - 1 - i, i, i) == Constants.CELL_EMPTY)
            {
              emptyCounter++;
              if(i == 1 || i == 2)
                  throughCentreEmptyCounter++;
            }
            else if(state.at(state.BOARD_SIZE - 1 - i, i, i) == Constants.CELL_O)
            {
              Ocounter++;
              if(i == 1 || i == 2)
                  throughCentreOCounter++;
            }
        }
        numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);

        for (int i = 0; i < state.BOARD_SIZE; i++)
        {
            counter = 0;
            Ocounter = 0;
            emptyCounter = 0;
            throughCentreCounter = 0;
            throughCentreEmptyCounter = 0;
            throughCentreOCounter = 0;
            for(int j = 0; j < state.BOARD_SIZE; j++)
            {
                if(state.at(state.BOARD_SIZE - 1 - j, i, j) == Constants.CELL_X)
                {
                    counter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreCounter++;
                }
                else if(state.at(state.BOARD_SIZE - 1 - j, i, j) == Constants.CELL_EMPTY)
                {
                    emptyCounter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreEmptyCounter++;
                }
                else if(state.at(state.BOARD_SIZE - 1 - j, i, j) == Constants.CELL_O)
                {
                    Ocounter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreOCounter++;
                }
            }
            numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);
        }

        for (int i = 0; i < state.BOARD_SIZE; i++)
        {
            counter = 0;
            Ocounter = 0;
            emptyCounter = 0;
            throughCentreCounter = 0;
            throughCentreEmptyCounter = 0;
            throughCentreOCounter = 0;
            for(int j = 0; j < state.BOARD_SIZE; j++)
            {
                if(state.at(j, i, state.BOARD_SIZE - 1 - j) == Constants.CELL_X)
                {
                    counter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreCounter++;
                }
                else if(state.at(j, i, state.BOARD_SIZE - 1 - j) == Constants.CELL_EMPTY)
                {
                    emptyCounter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreEmptyCounter++;
                }
                else if(state.at(j, i, state.BOARD_SIZE - 1 - j) == Constants.CELL_O)
                {
                    Ocounter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreOCounter++;
                }
            }
            numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);
        }

        for (int i = 0; i < state.BOARD_SIZE; i++)
        {
            counter = 0;
            Ocounter = 0;
            emptyCounter = 0;
            throughCentreCounter = 0;
            throughCentreEmptyCounter = 0;
            throughCentreOCounter = 0;
            for(int j = 0; j < state.BOARD_SIZE; j++)
            {
                if(state.at(i, j, state.BOARD_SIZE - 1 - j) == Constants.CELL_X)
                {
                    counter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreCounter++;
                }
                else if(state.at(i, j, state.BOARD_SIZE - 1 - j) == Constants.CELL_EMPTY)
                {
                    emptyCounter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreEmptyCounter++;
                }
                else if(state.at(i, j, state.BOARD_SIZE - 1 - j) == Constants.CELL_O)
                {
                    Ocounter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreOCounter++;
                }
            }
            numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);
        }


        for (int i = 0; i < state.BOARD_SIZE; i++)
        {
            counter = 0;
            Ocounter = 0;
            emptyCounter = 0;
            throughCentreCounter = 0;
            throughCentreEmptyCounter = 0;
            throughCentreOCounter = 0;
            for(int j = 0; j < state.BOARD_SIZE; j++)
            {
                if(state.at(i, state.BOARD_SIZE - 1 - j, j) == Constants.CELL_X)
                {
                    counter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreCounter++;
                }
                else if(state.at(i, state.BOARD_SIZE - 1 - j, j) == Constants.CELL_EMPTY)
                {
                    emptyCounter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreEmptyCounter++;
                }
                else if(state.at(i, state.BOARD_SIZE - 1 - j, j) == Constants.CELL_O)
                {
                    Ocounter++;
                    if((i == 1 || i == 2) && (j == 1 || j == 2))
                        throughCentreOCounter++;
                }
            }
            numberOfMarks += getHeuriticScore(counter, Ocounter, emptyCounter, throughCentreCounter, throughCentreEmptyCounter, throughCentreOCounter);
        }

        return numberOfMarks;
    }

    public static int getHeuriticScore(int counter, int oCounter, int emptyCounter, int throughCentreCounter, int throughCentreEmptyCounter, int throughCentreOCounter)
    {
        int numberOfMarks = 0;

        if (counter == 4)
        {
            numberOfMarks += 1000;
        }
        else if (counter == 3 && emptyCounter == 1)
        {
            numberOfMarks += 25;
            if (throughCentreCounter >= 1)
                numberOfMarks += 100;
        }
        else if (counter == 2 && emptyCounter == 2)
        {
            numberOfMarks += 3;
            if (throughCentreCounter >= 1)
                numberOfMarks += 1;
        }
        else if (counter == 1 && emptyCounter == 3)
        {
            numberOfMarks++;
            if (throughCentreCounter >= 1)
                numberOfMarks += 0;
        }
        else if (oCounter == 4)
        {
            numberOfMarks -= 1000;
        }
        else if (oCounter == 3 && emptyCounter == 1)
        {
            numberOfMarks -= 25;
            if (throughCentreOCounter >= 1)
                numberOfMarks -= 100;
        }
        else if (oCounter == 2 && emptyCounter == 2)
        {
            numberOfMarks -= 3;
            if (throughCentreOCounter >= 1)
                numberOfMarks -= 1;
        }
        else if (oCounter == 1 && emptyCounter == 3)
        {
            numberOfMarks--;
            if (throughCentreOCounter >= 1)
                numberOfMarks -= 1;
        }

        return numberOfMarks;
    }
}
