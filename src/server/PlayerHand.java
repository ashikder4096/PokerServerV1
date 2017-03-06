package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerHand {
	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(ArrayList<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public ArrayList<Card> getCombo() {
		return combo;
	}

	public void setCombo(ArrayList<Card> combo) {
		this.combo = combo;
	}

	public HandRanks getHandRank() {
		return handRank;
	}

	public void setHandRank(HandRanks handRank) {
		this.handRank = handRank;
	}
	private ArrayList<Card> playerCards;
	private ArrayList<Card> combo;
	private HandRanks handRank;
	
	
	
	public static void main(String[] args) {
		ArrayList<Card> cards = new ArrayList<>();
//		System.out.println(cards.size());
//		ArrayList<ArrayList<Card>> multiCards = new ArrayList<>();

//		private static final String[] RANKS =
//			{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
//
//		/**
//		 * The suits of the cards for this game to be sent to the deck.
//		 */
//		private static final String[] SUITS =
//			{"spades", "hearts", "diamonds", "clubs"};
//
//		/**
//		 * The values of the cards for this game to be sent to the deck.
//		 */
//		private static final int[] POINT_VALUES =
//			{14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
//		
//		Deck deck = new Deck(RANKS, SUITS, POINT_VALUES);
		
		
		Card a1 = new Card("A", "SPADES", 14);
		Card j1 = new Card("J", "SPADES", 11);	
		Card k1 = new Card("K", "SPADES", 13);
		Card ten1 = new Card("10", "SPADES", 10);
		Card q1 = new Card("Q", "SPADES", 12);	
		
		Card a2 = new Card("A", "HEARTS", 14);
		Card j2 = new Card("J", "HEARTS", 11);
		Card k2 = new Card("K", "HEARTS", 13);
		Card q2 = new Card("Q", "HEARTS", 12);
		Card ten2 = new Card("10", "HEARTS", 10);
		
		Card a3 = new Card("A", "DIAMOND", 14);
		
		Card a4 = new Card("A", "CLUBS", 14);
		
//		cards.add(a1); cards.add(k1); cards.add(q1); cards.add(j1); cards.add(ten1); //RoyalFlush && StraightFlush && flush && straight
//		cards.add(a2); cards.add(j1); cards.add(j2); cards.add(k1); cards.add(q1); cards.add(ten1); cards.add(a2); cards.add(a3); cards.add(a4);
		cards.add(new Card("3", "DIAMOND", 3)); cards.add(new Card("J", "SPADES", 11)); cards.add(new Card("8", "CLUBS", 8)); cards.add(new Card("4", "HEARTS", 4)) ; cards.add(new Card("2", "CLUBS", 2));
		
		PlayerHand hand = new PlayerHand(cards);
		
//		multiCards = hand.sameSuits(cards);
//		hand.displayMultiHand(multiCards);
//		hand.sortBySuit(cards);
//		hand.displayHand(cards);
		hand.getPlayerCombo();
		
//		System.out.println("isRoyalFlush: " + hand.isRoyalFlush(cards));
//		System.out.println("isStraightFlush: "+ hand.isStraightFlush(cards));
//		System.out.println("isFourOfAKind: " + hand.isFourOfAKind(cards));
//		System.out.println("IsFullHouse: " + hand.isFullHouse(cards));
//		System.out.println("isFlush: " + hand.isFlush(cards));
		
//		hand.sortByPointValue(cards);
//		hand.displayHand(cards);
		
		
//		cards.add(a1); cards.add(a2);
		
//		System.out.println(highCard(cards));
		
//		for(Integer i : cardsPointValue(cards))
//		{
//		{
//			System.out.println(i);
//		}
		System.out.println(hand.handRank);
		hand.displayCombo();
		System.out.println();
		hand.displayHand();
	}
	
	//Constructor
	public PlayerHand(ArrayList<Card> playerCards)
	{
		combo = new ArrayList<>();
		this.playerCards = playerCards;
	}
	
	/*
	 * Creating Combo
	 */
	
	
	public boolean isRoyalFlush()
	{
		ArrayList<Card> newCards= greatestSuits(playerCards); //get's the highest suit cards

		
		ArrayList<Integer> i = cardsPointValue(newCards);
		
		if (isSameSuits(newCards) && i.contains(10) && i.contains(11) && i.contains(12) && i.contains(13) && i.contains(14))
		{
			combo = newCards;
			return true;
		}
		else return false;
	}
	
	public boolean isStraightFlush()
	{
		ArrayList<Card> newCards= greatestSuits(playerCards); //get's the highest suit cards. Has to be 5

		sortByPointValue(newCards);
		ArrayList<Integer> i = cardsPointValue(newCards);
		
		if (i.contains(i.get(0) + 1) && i.contains(i.get(0) + 2) && i.contains(i.get(0) + 3) && i.contains(i.get(0) + 4))
		{
			combo = newCards;
			return true;
		}
		else return false;
		
	}

	public boolean isFourOfAKind()
	{
		sortByPointValue(playerCards);
		for(int i = 0 ; i < playerCards.size()-3 ; i++)
		{
			if((playerCards.get(i).pointValue() == playerCards.get(i+1).pointValue()) && (playerCards.get(i).pointValue() == playerCards.get(i+2).pointValue()) && (playerCards.get(i).pointValue() == playerCards.get(i+3).pointValue()))
			{
				combo.add(playerCards.get(i));
				combo.add(playerCards.get(i+1));
				combo.add(playerCards.get(i+2));
				combo.add(playerCards.get(i+3));
				return true;
			}
		}
		return false;
		
	}

	public boolean isFullHouse()
	{
		ArrayList<Card> tempCard = playerCards;
		boolean pair = false;
		sortByPointValue(tempCard);
		for(int i = 0 ; i < playerCards.size() - 1 ; i++)
		{
			if(tempCard.get(i).pointValue() == tempCard.get(i+1).pointValue())
			{
				pair = true;
				combo.add(playerCards.get(i));
				combo.add(playerCards.get(i+1));
				tempCard.remove(i);
				tempCard.remove(i+1);
				break;
			}
		}
		return pair && isThreeOfAKind();
	}

	public boolean isFlush()
	{
		ArrayList<Card> flush = greatestSuits(playerCards); //sets the highest suit to flush
		if(flush.size() == 5)
		{
			combo = greatestSuits(playerCards);
			return true;
		}
		return false;
	}

	public boolean isStraight()
	{
		for(int i = 0 ; i <= playerCards.size()-5 ; i++)
		{
			if(playerCards.get(i).pointValue()+1 == playerCards.get(i+1).pointValue() && playerCards.get(i).pointValue()+2 == playerCards.get(i+2).pointValue() && playerCards.get(i).pointValue()+3 == playerCards.get(i+3).pointValue() && playerCards.get(i).pointValue()+4 == playerCards.get(i+4).pointValue())
			{
				combo.add(playerCards.get(i));
				combo.add(playerCards.get(i+1));
				combo.add(playerCards.get(i+2));
				combo.add(playerCards.get(i+3));
				combo.add(playerCards.get(i+4));
				return true;
			}

		}
		return false;
	}

	public boolean isThreeOfAKind()
	{
		sortByPointValue(playerCards);
		for(int i = 0 ; i <= playerCards.size()-2 ; i++)
		{
			if((playerCards.get(i).pointValue() == playerCards.get(i+1).pointValue()) && (playerCards.get(i).pointValue() == playerCards.get(i+2).pointValue()))
			{
				combo.add(playerCards.get(i));
				combo.add(playerCards.get(i+1));
				combo.add(playerCards.get(i+2));
				return true;
			}
		}
		return false;
		
	}

	public boolean IsTwoPair()
	{
		int pair = 0;
		sortByPointValue(playerCards);
		for(int i = 0 ; i < playerCards.size() - 1 ; i++)
		{
			if(playerCards.get(i).pointValue() == playerCards.get(i+1).pointValue())
			{
				pair++;
				combo.add(playerCards.get(i));
				combo.add(playerCards.get(i+1));
				i++;
			}
		}
		if(pair >= 2)
		{
			return true;
		}
		else
		{
			combo.clear();
			return false;
		}
	}

	public boolean isPair()
	{
		sortByPointValue(playerCards);
		for(int i = 0 ; i < playerCards.size() - 1 ; i++)
		{
			if(playerCards.get(i).pointValue() == playerCards.get(i+1).pointValue())
			{
				combo.add(playerCards.get(i));
				combo.add(playerCards.get(i+1));
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the highest card in the deck
	 * @param cards The ArrayList of cards to be tested
	 * @return highest card in hand
	 */
	public Card highCard(ArrayList<Card> cards)
	{
		sortByPointValue(cards);
		return cards.get(cards.size()-1); //returns the last index
	}
	
	/**
	 * 
	 * @param cards A list of cards
	 * @return if the entire list contains the same suits
	 */
	public boolean isSameSuits(ArrayList<Card> cards)
	{
		String suit = cards.get(0).suit();
		for(Card c :cards)
		{
			if(!(c.suit().equals(suit)))//if suits r not equal
			{
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * Creates a multi-dimensional arrays of cards, organized by their suits whereas
	 * ArrayList(0) - CLUBS
	 * ArrayList(1) - DIAMOND
	 * ArrayList(2) - HEARTS
	 * ArrayList(3) - SPADES
	 * @param cards the user's hand
	 * @return MultiDimensional ArrayList of cards organized by their suits
	 */
	public ArrayList<ArrayList<Card>> sameSuits(ArrayList<Card> cards)
	{
		ArrayList<ArrayList<Card>> suits = new ArrayList<>();
		suits.add(new ArrayList<Card>()); suits.add(new ArrayList<Card>()); suits.add(new ArrayList<Card>()); suits.add(new ArrayList<Card>());
		for(Card x : cards)
		{
			if(x.suit().equals("CLUBS"))
			{
				suits.get(0).add(x);
			}
			if(x.suit().equals("DIAMOND"))
			{
				suits.get(1).add(x);
			}
			if(x.suit().equals("HEARTS")){
				suits.get(2).add(x);
			}
			if(x.suit().equals("SPADES"))
			{
				suits.get (3).add(x);
			}
		}
		return suits;
	}
	
	/**
	 * @param cards An ArrayList to check for the highest number of suits
	 * @return list of cards containing the highest number of repeatant suits
	 */
	public ArrayList<Card> greatestSuits(ArrayList<Card> cards)
	{
		ArrayList<ArrayList<Card>> suits = new ArrayList<>();
		suits = sameSuits(cards);
		
		ArrayList<Card> newCards= new ArrayList();
		for(ArrayList<Card> a : suits)
		{
			if(a.size()>newCards.size())
			{
				newCards = a;
			}
		}
		return newCards;
	}
	
	/**
	 * 
	 * @param cards sorts a list of cards by their suits
	 */
	public void sortBySuit(ArrayList<Card> cards)
	{
        for (int i=0; i<cards.size(); i++)
        {
        int min=i;
        for (int j = i+1; j<cards.size(); j++)
         {
             if( cards.get(j).suit().compareTo(cards.get(min).suit()) < 0)
             {
                 min = j;
             }
         }
         Collections.swap(cards, i, min);
        }
	}
	
	/**
	 * Sorts an arraylist by their pointValues
	 * Suits are taken into accounts
	 * Higher suits get's priority
	 * @param cards ArrayList to be sorted (least to greatest)
	 */
	public void sortByPointValue(ArrayList<Card> cards)
	{
        for (int i=0; i<cards.size(); i++)
        {
        int min=i;
        for (int j = i+1; j<cards.size(); j++)
        {
        	if( cards.get(j).pointValue() < cards.get(min).pointValue()) //if current card is lower than testing card
            {
                min = j;
            }
        	if( cards.get(j).pointValue() == cards.get(min).pointValue()) //if they are the same suit, place higher suit after lower
            {
                if(cards.get(j).suit().compareTo(cards.get(min).suit()) < 0)
                {
                	min = j;
                }
            }
        }
        Collections.swap(cards, i, min);
        }
	}
	
	/**
	 * Converts a list of cards into a list of integer of their pointValues
	 * @param cards a list of cards
	 * @return ArrayList<Intger> of the card's point values
	 */
	public ArrayList<Integer> cardsPointValue(ArrayList<Card> cards)
	{
		ArrayList<Integer> a = new ArrayList<>();
		{
			for (Card c : cards)
			{
				a.add(c.pointValue());
			}
		}
		return a;
	}
	
	/**
	 * Outputs your hand
	 * @param playerCards an ArrayList of user's hand
	 */
	public void displayHand()
	{
		if(playerCards != null)
		{
			for(Card c : playerCards)
			{
				System.out.println(c.toString());
			}
		}
	}
	public void displayCombo()
	{
		if(combo != null)
		{
			for(Card c : combo)
			{
				System.out.println(c.toString());
			}
		}
	}
	
	/**
	 * Displays multiDimensional ArrayList of cards
	 * @param cards multiDimensional ArrayList of cards
	 */
	public void displayMultiHand(ArrayList<ArrayList<Card>> cards)
	{
		for(ArrayList<Card> c : cards)
		{
			for(Card k : c)
			{
				System.out.println(k.toString());
			}
			System.out.println();
		}
	}
	public ArrayList<Card> getPlayerCombo()
	{
		combo.clear();
		if(isRoyalFlush())
		{
			handRank = HandRanks.Royal_Flush;
		}
		else if(isStraightFlush())
		{
			handRank = HandRanks.Straight_Flush;
		}
		else if(isFourOfAKind())
		{
			handRank = HandRanks.Four_Of_A_Kind;
		}
		else if(isFullHouse())
		{
			handRank = HandRanks.Full_House;
		}
		else if(isFlush())
		{
			handRank = HandRanks.Flush;
		}
		else if(isStraight())
		{
			handRank = HandRanks.Straight;
		}
		else if(isThreeOfAKind())
		{
			handRank = HandRanks.Three_Of_A_Kind;
		}
		else if(IsTwoPair())
		{
			handRank = HandRanks.Two_Pair;
		}
		else if(isPair())
		{
			handRank = HandRanks.One_Pair;
		}
		else
		{
			combo.add(highCard(playerCards));
			handRank = HandRanks.High_Card;
		}
		return combo;
	}

}
