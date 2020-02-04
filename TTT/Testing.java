import java.util.Vector;
public class Testing
{
	public static void main(String[] args)
	{
		GameState game = new GameState();
		Player p1 = new Player();
		long start = System.currentTimeMillis();

		GameState newGamestate = p1.play(game, new Deadline(10));
		long end = System.currentTimeMillis();

		long timeElapsed = end-start;
		System.out.println(timeElapsed/1000);
		/*Vector<GameState> possibleStates = new Vector<GameState>();

		game.findPossibleMoves(possibleStates);

		for (int i = 0; i < possibleStates.size(); i++)
		{
			System.out.println(possibleStates.get(i).toString(Constants.CELL_X));
		}

		game = possibleStates.get(0);
		possibleStates.clear();
		game.findPossibleMoves(possibleStates);

		for (int i = 0; i < possibleStates.size(); i++)
		{
			System.out.println(possibleStates.get(i).toString(Constants.CELL_O));
		}*/
	}
}
