package server;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class Poker{
	/**
	 * Broadcast Keywords
	 * #Start: (INT)StartingBalance, (B)isPlaying
	 * #PlayerTurn
	 */

	public static void main(String[] args) {
		Poker p = new Poker();
		p.activePlayers.add(new Player("User0"));
		p.activePlayers.add(new Player("User1"));
		p.activePlayers.add(new Player("User2"));
		
		Card a1 = new Card("A", "SPADES", 14);
		Card j1 = new Card("J", "SPADES", 11);	
		Card k1 = new Card("K", "SPADES", 13);
		Card ten1 = new Card("10", "SPADES", 10);
		Card q1 = new Card("Q", "SPADES", 12);
		
		Card nineS = new Card("9", "SPADES", 9);
		Card tenD = new Card("10", "DIAMOND", 10);
		Card jC = new Card("J", "CLUBS", 11);
		Card qH = new Card("Q", "HEARTS", 12);
		Card kS = new Card("K", "SPADES", 13);
		Card kD = new Card("K", "DIAMOND", 13);
		
		ArrayList<Card> royalFlush = new ArrayList<>();
		ArrayList<Card> straightHighDiamond = new ArrayList<>();
		ArrayList<Card> straightHighSpades = new ArrayList<>();
		
		royalFlush.add(a1); royalFlush.add(j1); royalFlush.add(k1); royalFlush.add(ten1); royalFlush.add(q1);
		straightHighDiamond.add(nineS); straightHighDiamond.add(tenD); straightHighDiamond.add(jC); straightHighDiamond.add(qH); straightHighDiamond.add(kD);
		straightHighSpades.add(nineS); straightHighSpades.add(tenD); straightHighSpades.add(jC); straightHighSpades.add(qH); straightHighSpades.add(kS);
		
		p.activePlayers.get(0).setHand(straightHighDiamond);
		p.activePlayers.get(1).setHand(royalFlush);
		p.activePlayers.get(2).setHand(straightHighSpades);
		
		
		for(Player P : p.activePlayers)
		{
			System.out.println(P.toString());
			P.initializePlayerHand();
			System.out.println(P.playerHand().getHandRank());
		}
		
		System.out.println();
		p.rankPlayers();
		for(Player P : p.activePlayers)
		{
			System.out.println(P.toString());
		}
		
	}
	
	private Server server;
	private ArrayList<Player> players, activePlayers;
	
	private int startingBalance = 0;
	private int Round = 0;
	private int requiredBet = 0;
	private int pot = 0;
	private Deck deck;
	private Random r = new Random();
	
//	private int SmallBlind = -1, BigBlind = 0, CurrentPlayer = 0;
	private ArrayList<Card> communityCards;
	private Player Winner;
	
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
	
	
	//Getters and Setters
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Player> getActivePlayers() {
		return activePlayers;
	}

	public void setActivePlayers(ArrayList<Player> activePlayers) {
		this.activePlayers = activePlayers;
	}

	public int getStartingBalance() {
		return startingBalance;
	}

	public void setStartingBalance(int startingBalance) {
		this.startingBalance = startingBalance;
	}

	public int getRound() {
		return Round;
	}
	
	public void setRound(int round) {
		Round = round;
	}

	//Constructors
	public Poker() //for Testing
	{
		players = new ArrayList<>();
		activePlayers = new ArrayList<>();
		communityCards = new ArrayList<>();
	}

	public void setCommunityCards(ArrayList<Card> communityCards) {
		this.communityCards = communityCards;
	}

	public Poker(Server server, ArrayList<Player> players, int startingBalance)
	{
		this.server = server;
		this.players = players;
		this.startingBalance = startingBalance;
		
		activePlayers = new ArrayList<>();
		communityCards = new ArrayList<>();
		deck  = new Deck(RANKS, SUITS, POINT_VALUES);
	}
	
	//Beginning of logic
	public void start()
	{
		activePlayers = new ArrayList<>(players);
		for(Player p : activePlayers)
		{
			//p.Write().writeUTF("#Start");
			p.addBalance(startingBalance);
		}
		while(activePlayers.size() >= 2)
		{
			newGame();
		}
	}
	
//	public ArrayList<Player> setRotation()
//	{
//		int startingPlayer = 
//		ArrayList<Player> pl = new ArrayList<>();
//		for(int i = startingPlayer; i< activePlayers.size() ;  i++)
//		{
//			pl.add(activePlayers.get(i)); //get's player and adds to pl (current position to the end)
//		}
//		for(int i = 0 ; i < startingPlayer ; i++) //startings from 0 and goes to starting player
//		{
//			pl.add(activePlayers.get(i));
//		}
//		return pl;
//	}
	
	public void newGame()
	{
		deck.setSize((RANKS.length) * (SUITS.length));
		deck.shuffle();
		pot = 0;
		activePlayers = new ArrayList<>(players);
		communityCards.clear();
		for(Player p : players)
		{
			p.setPlaying(true);
			p.clearHand();
		}
		
		Round = 0;
			for(Player p : activePlayers) //gives out two cards to each players
			{
				p.addToHand(deck.deal());
				p.addToHand(deck.deal());
			}
			//sets the big blind nd small Blinds
			activePlayers.get(0).setSmallBlind(true);
			activePlayers.get(1).setBigBlind(true);		
			
			getBets(); //starts the betting
		
		Round = 1;
			//adds three card to community card
			communityCards.add(deck.deal()); 
			communityCards.add(deck.deal()); 
			communityCards.add(deck.deal()); 
			getBets();
			
		Round = 2;
			communityCards.add(deck.deal());			
			getBets();
			
		Round = 3;
			communityCards.add(deck.deal());
			getBets();
			rankPlayers();
			server.broadcastToAll("Winner is: " + Winner);
			Winner.addBalance(pot);
	}
	
	public boolean equalBet()
	{
		for(Player p : activePlayers)
		{
			if(p.getBet() != requiredBet)
			{
				return false;
			}
		}
		return true;
	}
	
	public void getBets()
	{
		requiredBet = 0;
		for(Player p : activePlayers)
		{
			if(p.getPlayerAction().equals(PlayerAction.RAISE))
			{
				requiredBet = p.getPlayerAction().getAmount();
				pot += p.getBet();
			
				while(!equalBet())
				{
					for(Player q : activePlayers)
					{
						if(q.getBet() != requiredBet) //If they raise or fold
						{
							PlayerAction action = q.getPlayerAction();
							if(action == PlayerAction.FOLD) //if they fold
							{
								activePlayers.remove(q);
							}
							else//if they raise
							{
								requiredBet = q.getBet();
								pot += q.getBet();
							}
						}

					}
				}
			}
		}
	}
	
	public void rankPlayers()
	{
		for(Player p : activePlayers)
		{
	        p.initializePlayerHand();
		}
		for (int i=0; i<activePlayers.size(); i++) //sort from least to greatest
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
	        		Card pj = activePlayers.get(j).playerHand().highCard(activePlayers.get(j).playerHand().getCombo());
	        		Card pmin= activePlayers.get(min).playerHand().highCard(activePlayers.get(min).playerHand().getCombo());
	        		if( pj.comparedTo(pmin) == -1)
	        		{
	        			min = j;
	        		}
	            }
			}
        Collections.swap(activePlayers, i, min);
        }
		Winner = activePlayers.get(activePlayers.size()-1);
	}
}
