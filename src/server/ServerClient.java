package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class ServerClient {

	private final Socket socket;
	private final ExecutorService executorService;
	private List<ServerClient> connections = new ArrayList<ServerClient>();
	private String userName;
	
	ServerClient(Socket socket,ExecutorService executorService,List<ServerClient> connections ) {
		this.socket = socket;
		this.executorService = executorService;
		this.connections = connections;
		receive();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Socket getSocket() {
		return socket;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public List<ServerClient> getConnections() {
		return connections;
	}

	public void receive() {
		new ServerReceiver(this).receive();
	}

	public void send(String data) {
		new ServerSender(this).send(data);
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
