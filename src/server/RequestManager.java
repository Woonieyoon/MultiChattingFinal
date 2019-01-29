package server;

import java.util.HashMap;
import java.util.Map;

public class RequestManager {
	private ServerClient serverClient;
	Map<String, SplitedTokenProcessor> map = new HashMap<>();

	public RequestManager(ServerClient serverClient) {
		this.serverClient = serverClient;
		map.put("name", new ServerClientSetUserName(serverClient));
		map.put("send", new ServerSendAllClient(serverClient));
	}

	public Map<String, SplitedTokenProcessor> getMap() {
		return map;
	}

	public void add() {

	}

}
