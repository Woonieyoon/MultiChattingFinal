package server.room;

import java.net.Socket;

import server.ServerClient;
import server.splitprocessor.SplitedTokenProcessor;

public class RoomEnter implements SplitedTokenProcessor {

	private ServerClient serverClient;

	public RoomEnter(ServerClient serverClient) {
		this.serverClient = serverClient;
	}

	@Override
	public void Process(Socket socket, String roomTitle) {
		synchronized (serverClient) {
			serverClient.getRoom().ExitRoom(serverClient);
			System.out.println("방 사이즈:" + serverClient.getRoomManager().getRoomList());
			for (Room room : serverClient.getRoomManager().getRoomList()) {
				System.out.println(room.getRoomTitle() + "/방제목/" + roomTitle);
				if (room.getRoomTitle().equals(roomTitle)) {
					room.EnterRoom(serverClient);
					serverClient.setRoom(room);
					serverClient.send(roomTitle + " 방에 입장하셨습니다.");
				}
			}
		}
	}

}
