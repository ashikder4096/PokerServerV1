package server;

public enum PlayerAction {
	FOLD(24),
	CHECK(0),
	CALL(0),
	RAISE(0);
	
	public static void main(String[] args) {
		PlayerAction action = null;
		action = action.CALL;
		action.setAmount(24);
		System.out.println(action);
		System.out.println(PlayerAction.FOLD == action);
	}
	
	private int Amount;
	PlayerAction(int Amount)
	{
		this.Amount = Amount;
	}
	
	public void setAmount(int Amount)
	{
		this.Amount = Amount;
	}
	
	public int getAmount()
	{
		return Amount;
	}
	
	public static PlayerAction toPlayerAction(String str)
	{
		switch(str)
		{
		case("FOLD"):
			return PlayerAction.FOLD;
		
		case("CHECK"):
			return PlayerAction.CHECK;
		
		case("CALL"):
			return PlayerAction.CALL;
		
		case("RAISE"):
			return PlayerAction.RAISE;
		default:
			return null;
		}
	}
}
