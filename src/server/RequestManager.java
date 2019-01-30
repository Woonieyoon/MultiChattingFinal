package server;

import java.util.HashMap;
import java.util.Map;

import server.room.RoomCreate;
import server.room.RoomEnter;
import server.room.RoomExit;

public class RequestManager {

	private ServerClient serverClient;
	Map<String, SplitedTokenProcessor> map = new HashMap<>();

	public RequestManager(ServerClient serverClient) {
		this.serverClient = serverClient;
		map.put("name", new ServerClientSetUserName(this.serverClient));
		map.put("send", new ServerSendAllClient(this.serverClient));
		map.put("userlist", new ServerShowList(this.serverClient));
		map.put("makeRoom", new RoomCreate(this.serverClient));
		map.put("enterRoom", new RoomEnter(this.serverClient));
		map.put("exitRoom", new RoomExit(this.serverClient));
	}

	public Map<String, SplitedTokenProcessor> getMap() {
		return map;
	}

	public void add() {

	}

}
