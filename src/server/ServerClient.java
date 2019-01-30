package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import server.room.Room;
import server.room.RoomManager;


public class ServerClient {

	private final Socket socket;
	private final ExecutorService executorService;
	private List<ServerClient> connections = new ArrayList<ServerClient>();
	private String userName;
	private RoomManager roomManager;
	private Room room;
		
	ServerClient(Socket socket,ExecutorService executorService,List<ServerClient> connections ,RoomManager roomManager) {
		this.socket = socket;
		this.executorService = executorService;
		this.connections = connections;
		this.roomManager = roomManager;
		init();
		receive();
	}

	public void init() {
		for (Room tempRoom : roomManager.getRoomList()) {
			if (tempRoom.getRoomTitle().equals("All"))
			{	
				tempRoom.EnterRoom(this);
				room = tempRoom;
			}
		}
	}
	
	public RoomManager getRoomManager() {
		return roomManager;
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
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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
