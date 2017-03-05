package server;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
	private ServerSocket server;
	private Socket nUser;
	private DataInputStream input;
	private int port;
	
	private ArrayList<Player> players = new ArrayList<>();
	private int numOfPlayer;
	
	int startingBalance = 0;
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Server(int port, int numOfPlayers, int startingBalance)
	{
		this.port = port;
		this.numOfPlayer = numOfPlayers;
		this.startingBalance = startingBalance;
	}
	
	public void start()
	{
		try {
			server = new ServerSocket(this.port);
			
			waitForUser(numOfPlayer);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	private void waitForUser(int numOfPlayers) {
		for(int i = 1 ; i <= numOfPlayers ; i++)
		{
			try {
				System.out.println("Waiting for "+ i + " more player to connect");
				nUser = server.accept();
				players.add(new Player(nUser, this));
				System.out.println(players.get(i-1).getUsername() + " has connected!");
				broadcastToAll(players.get(i-1).getUsername() + " has connected!");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Everyone has connected");
		Scanner sc = new Scanner(System.in);
		while(true)
		{
			String message = sc.nextLine();
			broadcastToAll(message);
		}
	}
	
	public void broadcastToAll(String message)
	{
		for(Player p : players)
		{
			try {
				p.Write().writeUTF("CHAT# Server: " + message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
