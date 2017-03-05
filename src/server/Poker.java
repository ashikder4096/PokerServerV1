package server;
import java.io.IOException;
import java.util.ArrayList;
public class Poker{
	/**
	 * Broadcast Keywords
	 * #Start: (INT)StartingBalance, (B)isPlaying
	 * #PlayerTurn
	 */
	
	private Server server;
	private ArrayList<Player> players, activePlayers;
	private boolean wait;
	
	private int startingBalance;
	private int Round = 0;
	private int SmallBlind = -1, BigBlind = 0, CurrentPlayer = 0;
	
	public Poker(Server server, ArrayList<Player> players, int startingBalance)
	{
		this.server = server;
		this.players = players;
		this.startingBalance = startingBalance;
	}
	
	public void start()
	{
		activePlayers = new ArrayList<>(players);
		for(Player p : activePlayers)
		{
			try {
				p.Write().writeUTF("#Start");
				//Send starting balance, isPlaying
				p.Write().writeInt(startingBalance);
				p.Write().writeBoolean(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}
	
	public static ArrayList<Player> setRotation(ArrayList<Player> players, int startingPlayer)
	{
		ArrayList<Player> pl = new ArrayList<>();
		for(int i = startingPlayer; i< players.size() ;  i++)
		{
			pl.add(players.get(i)); //get's player and adds to pl
		}
		for(int i = 0 ; i < startingPlayer ; i++)
		{
			pl.add(players.get(i)); //get's player and adds to pl
		}
		return pl;
	}
	
	public void startRound()
	{
		
	}
}
