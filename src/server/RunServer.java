package server;
public class RunServer {
	public static void main(String[] args) {
		Server server = new Server(5342, 1, 1000);
		server.start();
	}	
}
