package server.room;

import java.net.Socket;

import server.ServerClient;
import server.splitprocessor.SplitedTokenProcessor;

public class RoomCreate implements SplitedTokenProcessor{

	ServerClient serverclient;
	
	public RoomCreate(ServerClient serverClient) {
		 this.serverclient = serverClient;
	}

	@Override
	public void Process(Socket socket, String data) {
		synchronized (serverclient) {
			serverclient.getRoomManager().createRoom(data);
		}
	}
	
}
