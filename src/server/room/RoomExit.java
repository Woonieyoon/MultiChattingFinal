package server.room;

import java.net.Socket;

import server.ServerClient;
import server.splitprocessor.SplitedTokenProcessor;

public class RoomExit implements SplitedTokenProcessor {

	private ServerClient serverClient;

	public RoomExit(ServerClient serverClient) {
		this.serverClient = serverClient;
	}

	@Override
	public void Process(Socket socket, String data) {
		synchronized (serverClient) {
			String roomTitle = serverClient.getRoom().getRoomTitle();
			serverClient.getRoom().ExitRoom(serverClient);
			for (Room room : serverClient.getRoomManager().getRoomList()) {
				if (room.getRoomTitle().equals("All")) {
					room.EnterRoom(serverClient);
					serverClient.setRoom(room);
					break;
				}
			}
			serverClient.send(roomTitle + " 방을 나가셨습니다.");
		}
	}
}
