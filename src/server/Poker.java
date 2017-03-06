package server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	
	private static final String[] RANKS =
		{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

	/**
	 * The suits of the cards for this game to be sent to the deck.
	 */
	private static final String[] SUITS =
		{"spades", "hearts", "diamonds", "clubs"};

	/**
	 * The values of the cards for this game to be sent to the deck.
	 */
	private static final int[] POINT_VALUES =
		{14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	
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
			pl.add(players.get(i)); //get's player and adds to pl (current position to the end)
		}
		for(int i = 0 ; i < startingPlayer ; i++) //startings from 0 and goes to starting player
		{
			pl.add(players.get(i));
		}
		return pl;
	}
	
	public void startRound()
	{
		
	}
	
	public void rankPlayers()
	{
		for(Player p : activePlayers)
		{
	        p.initializePlayerHand();
		}
		for (int i=0; i<activePlayers.size(); i++)
        {
        int min=i;
        for (int j = i+1; j<activePlayers.size(); j++)
        {
        	if(activePlayers.get(j).playerHand().getHandRank().compareTo(activePlayers.get(min).playerHand().getHandRank()) > 0)
            {
                min = j;
            }
        	else if((activePlayers.get(j).playerHand().getHandRank().compareTo(activePlayers.get(min).playerHand().getHandRank()) == 0)) //Tie_Breaker
            {
        		if(activePlayers.get(j).playerHand().getHandRank().equals(HandRanks.Royal_Flush))
        		{
        			
        		}
        		Card tempCj = activePlayers.get(j).playerHand().highCard(activePlayers.get(j).hand());
        		Card tempCmin = activePlayers.get(min).playerHand().highCard(activePlayers.get(min).hand());
//        		if()
            }
        }
        Collections.swap(activePlayers, i, min);
        }
	}
}
