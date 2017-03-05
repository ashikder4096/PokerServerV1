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
	
	private ArrayList<Card> Hand;
	private boolean isPlaying = false;
	
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
	
	public void setUsername(String username)
	{
		this.username = username;
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
	
	//PokerLogic
}
