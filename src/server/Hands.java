package server;
public enum Hands {
	Royal_Flush(9),
	Straight_Flush(8),
	Four_Of_A_Kind(7),
	Full_House(6),
	Flush(5),
	Straight(4),
	Three_Of_A_Kind(3),
	Two_Pair(2),
	One_Pair(1),
	High_Card(0);
	
	int value;
	Hands(int value)
	{

	}
	
	public int getValue()
	{
		return value;
	}
}
