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
		
		Thread t = new Thread(this);
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

	@Override
	public void run() {
		while(true)
		{
			String message = null;
			try {
				message = read.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
