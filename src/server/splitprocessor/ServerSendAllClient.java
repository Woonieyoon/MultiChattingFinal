package server.splitprocessor;

import java.net.Socket;

import server.ServerClient;

public class ServerSendAllClient implements SplitedTokenProcessor {

	private ServerClient serverClient;

	public ServerSendAllClient(ServerClient serverClient) {
		this.serverClient = serverClient;
	}

	@Override
	public void Process(Socket socket, String data) {
		synchronized (serverClient) {
			for (ServerClient client : serverClient.getRoom().getUserList()) {
				if (client.getSocket() == socket)
					continue;
				client.send(serverClient.getUserName() + ">>" + data);
			}
		}

//		for (ServerClient client : serverClient.getConnections()) {
//			if (client.getSocket() != socket) {
//				client.send(serverClient.getUserName() + ">>" + data);
//			}
//		}
	}

}
