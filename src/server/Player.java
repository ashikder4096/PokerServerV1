package server;
import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

public class Player implements Runnable{


	private String username;
	private Socket connection;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private DataInputStream read;
	private DataOutputStream write;
	private Server server;
	
	Thread t = new Thread(this);
	
	private PlayerHand playerhand;
	
	private int bet = 0;
	private int Balance = 0;
	
	private PlayerAction action;
	private boolean isBigBlind = false, isSmallBlind = false;
	
	private ArrayList<Card> Hand;
	
	private boolean isPlaying = false;
	
	public int getBet()
	{
		return bet;
	}
	
	public void setHand(ArrayList<Card> Hand)
	{
		this.Hand = Hand;
	}

	/**
	 * broadcast "#clearHand" to clear user hand
	 */
	public void clearHand() {
		try {
			write.writeUTF("#clearHand");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Hand.clear();
	}
	
	/**
	 * adds card to Hand using keyboard "#addToHand"
	 * sends a card as string
	 * @param c
	 */
	public void addToHand(Card c)
	{
		Hand.add(c);
		try {
			write.writeUTF("#addToHand");
			write.writeUTF(c.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initializePlayerHand()//meant to be used at the end of the game
	{
		playerhand = new PlayerHand(Hand);
		playerhand.getPlayerCombo();
	}
	
	public PlayerHand playerHand() {
		return playerhand;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	/**
	 * sends isPlaying with keyboard #isPlaying
	 * @param isPlaying
	 */
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
		try {
			write.writeUTF("#isPlaying");
			write.writeBoolean(isPlaying);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Player(String username) //used for normal testing
	{
		this.username = username;
	}
	
	public Player(Socket socket, Server server) {
		connection = socket;
		this.server = server;
		setupStream();
		try {
			username = read.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.start();
	}
	
	private void setupStream() {
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
			write = new DataOutputStream(connection.getOutputStream());
			System.out.println("Player's output stream has been setup");
			output.flush();
			write.flush();
			
			input = new ObjectInputStream(connection.getInputStream());
			read = new DataInputStream(connection.getInputStream());
			System.out.println("Player's input stream has been setup");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getUsername() {
		return username;
	}

	public Socket Socket() {
		return connection;
	}

	public ObjectInputStream Input() {
		return input;
	}

	public ObjectOutputStream Output() {
		return output;
	}
	
	public DataInputStream Read() {
		return read;
	}

	public DataOutputStream Write() {
		return write;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while(true)
		{
			String message = null;
			try {
				message = read.readUTF();
				
			} catch (IOException e) { //checks if user is still connected. If not, wait for another user
				// TODO Auto-generated catch block
				broadcastToAll("User (" + username + ") has diconnected");
				broadcastToAll("Waiting for another player to join");
				try {
					connection.close();
					write.close();
					read.close();
					System.out.println("User (" + username + ") has diconnected");
					System.out.println("Connection successfully closed");
					isPlaying = false;
					server.getPlayers().remove(this);
					System.out.println("Player has been removed from the list");
					System.out.println("Waiting for another user to join");
					server.waitForAPlayer();
					t.stop();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					
				}
				
				
			}
			
			if(message.contains("CHAT# "))
			{
				String chat = this.username + ": " + message.substring(6);
				System.out.println(chat);
				broadcastToAll(chat);
			}
		}
		
	}
	
	public void broadcastToAll(String chat)
	{
		for(Player p : server.getPlayers())
		{
			try {
				p.Write().writeUTF("CHAT# " + chat);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Card> mergeCards(ArrayList<Card> a1, ArrayList<Card> a2) //Will be used to combine player hand and community card
	{
		ArrayList<Card> arr = new ArrayList<>(a1);
		for(Card c : a2)
		{
			arr.add(c);
		}
		return arr;
	}
	
	/**
	 * sends out "#getPlayerAction
	 * Should receive: 
	 * 1) UTF(PlayerAction as String)
	 * 2) INT(actionAmount)
	 * 
	 * Also sets bet to action.getAmount();
	 * @return playerAction
	 */
	public PlayerAction getPlayerAction()
	{
		try {
			write.writeUTF("#getPlayerAction");
			action = PlayerAction.toPlayerAction(read.readUTF());
			action.setAmount(read.readInt());
			Balance -= action.getAmount();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bet = action.getAmount();
		isBigBlind = false;
		isSmallBlind = false;
		return action;
	}
	
	public boolean isBigBlind() {
		return isBigBlind;
	}

	/**
	 * Sends out if is BigBlind with keyword #BigBlind
	 * @param isBigBlind
	 */
	public void setBigBlind(boolean isBigBlind) {
		this.isBigBlind = isBigBlind;
		try {
			write.writeUTF("#BigBlind");
			write.writeBoolean(isBigBlind);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isSmallBlind() {
		return isSmallBlind;
	}

	/**
	 * Sets smallBlind using keyboard "#SmallBlind"
	 * @param isSmallBlind
	 */
	public void setSmallBlind(boolean isSmallBlind) {
		this.isSmallBlind = isSmallBlind;
		try {
			write.writeUTF("#SmallBlind");
			write.writeBoolean(isSmallBlind);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sends out the amount to be added to player balance
	 * @param amount
	 */
	public void addBalance(int amount)
	{
		try {
			write.writeUTF("#addBalance");
			write.writeInt(amount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Balance += amount;
	}
	
	public String toString()
	{
		return username;
	}
	
	//PokerLogic
}
