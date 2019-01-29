package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	private final int port;
	private ServerSocket serverSocket;

	public Server(int port) {
		this.port = port;
	}

	public void initialize() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			if (!serverSocket.isClosed()) {
				close();
			}
			return;
		}
	}

	public void execute() {
		ServerManager manager = new ServerManager(serverSocket);
		manager.init();
		manager.execute();
	}

	void close() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(10020);
		server.initialize();
		server.execute();
	}

}
