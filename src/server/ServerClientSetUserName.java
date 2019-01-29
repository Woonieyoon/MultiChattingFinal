package server;

import java.net.Socket;

public class ServerClientSetUserName implements SplitedTokenProcessor {

	ServerClient serverclient;

	public ServerClientSetUserName(ServerClient serverclient) {
		this.serverclient = serverclient;
	}

	@Override
	public void Process(Socket socket, String data) {
		serverclient.setUserName(data);
		System.out.println("유저 이름" + serverclient.getUserName());
	}

}
