package server;
/*
 * Asif Shikder && Darren Kong
 * Period 7th
 * Levin
 * CS Java
 */


/**
 * Card.java
 *
 * <code>Card</code> represents a playing card.
 */
public class Card {

	/**
	 * String value that holds the suit of the card
	 */
	private String suit;

	/**
	 * String value that holds the rank of the card
	 */
	private String rank;

	/**
	 * int value that holds the point value.
	 */
	private int pointValue;


   /**
	 * Creates a new <code>Card</code> instance.
	 *
	 * @param cardRank  a <code>String</code> value
	 *                  containing the rank of the card
	 * @param cardSuit  a <code>String</code> value
	 *                  containing the suit of the card
	 * @param cardPointValue an <code>int</code> value
	 *                  containing the point value of the card
	 */
	public Card(String cardRank, String cardSuit, int cardPointValue) {
		/* *** TO BE IMPLEMENTED IN ACTIVITY 1 *** */
		rank = cardRank;
		suit = cardSuit;
		pointValue = cardPointValue;
	}


	/**
	 * Accesses this <code>Card's</code> suit.
	 * @return this <code>Card's</code> suit.
	 */
	public String suit() {
		return suit;
   }

	/**
	 * Accesses this <code>Card's</code> rank.
	 * @return this <code>Card's</code> rank.
	 */
	public String rank() {
		return rank;
	}

   /**
	 * Accesses this <code>Card's</code> point value.
	 * @return this <code>Card's</code> point value.
	 */
	public int pointValue() {
		return pointValue;
	}

	/** Compare this card with the argument.
	 * @param otherCard the other card to compare to this
	 * @return true if the rank, suit, and point value of this card
	 *              are equal to those of the argument;
	 *         false otherwise.
	 */
	

	public boolean matches(Card otherCard) {
		/* *** TO BE IMPLEMENTED IN ACTIVITY 1 *** */
		return otherCard.rank() == this.rank() && otherCard.suit() == this.suit() && otherCard.pointValue() == this.pointValue();
	}

	/**
	 * Converts the rank, suit, and point value into a string in the format
	 *     "[Rank] of [Suit] (point value = [PointValue])".
	 * This provides a useful way of printing the contents
	 * of a <code>Deck</code> in an easily readable format or performing
	 * other similar functions.
	 *
	 * @return a <code>String</code> containing the rank, suit,
	 *         and point value of the card.
	 */
	@Override
	public String toString() {
		if(this == null)
		{
			return "";
		}
		return "[" + rank + "] of [" + suit + "] (point value = [" + pointValue +"])";
	}
	
	public static Card toCard(String str)
	{
		String Rank = "", Suit = "", PV = "";

		
		for (int i = 0 ; i < str.length() - 1 ; i++)
		{
			if(str.substring(i, i+1).equals("["))
			{
				if(Rank.equals(""))
				{
					for (int j = i + 1 ; !(str.substring(j,j+1).equals("]")); j++)
					{
						Rank += str.substring(j,j+1);
					}
				}
				else if(Suit.equals(""))
				{
					for (int k = i + 1 ; !(str.substring(k, k+1).equals("]")); k++)
					{
						Suit += str.substring(k,k+1);
					}
				}
				else// (PV.equals(""))
				{
					for (int l = i + 1 ; !(str.substring(l,l+1).equals("]")); l++)
					{
						PV += str.substring(l,l+1);
					}
				}
			}
		}
		int pointValue = Integer.parseInt(PV);
		return new Card(Rank,Suit,pointValue);
	}
}
