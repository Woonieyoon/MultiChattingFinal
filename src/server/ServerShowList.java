package server;

import java.net.Socket;

import server.room.Room;
import server.room.RoomManager;

public class ServerShowList implements SplitedTokenProcessor {
	
	private ServerClient serverClient;  
	
	public ServerShowList(ServerClient serverClient){
		this.serverClient = serverClient;
	}

	@Override
	public void Process(Socket socket, String data) {
		synchronized (serverClient) {
			StringBuilder userNames=new StringBuilder();
			
			if(serverClient.getRoomManager().getRoomList().size() > 0) {
				userNames.append("----방 리스트 ----").append("\n");
				RoomManager rm =serverClient.getRoomManager();
				for(Room room:rm.getRoomList()) {
					userNames.append(room.getRoomTitle()).append("\n");
				}
			}
			
			userNames.append("----접속자----").append("\n");
			userNames.append("총 인원: "+serverClient.getConnections().size()).append("\n");
			ServerClient requestClient=null;
			for (ServerClient client : serverClient.getConnections()) {
					userNames.append(client.getUserName()).append("\n");
					if(client.getSocket()!=socket)
						continue;
					requestClient = client;
					
			}
			requestClient.send(userNames.toString());
		}
	}
}
