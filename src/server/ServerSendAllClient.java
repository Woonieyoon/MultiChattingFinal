package server;

import java.net.Socket;

public class ServerSendAllClient implements SplitedTokenProcessor {

	private ServerClient serverClient;

	public ServerSendAllClient(ServerClient serverClient) {
		this.serverClient = serverClient;
	}

	@Override
	public void Process(Socket socket, String data) {
		System.out.println("시작합니다.");
		System.out.println(serverClient.getConnections() + "갯수");
		synchronized (serverClient) {
			for (ServerClient client : serverClient.getConnections()) {
				System.out.println("왔다갔다");
				if (client.getSocket() != socket) {
					System.out.println("와봐");
					client.send(serverClient.getUserName() + ">>" + data);
				}
			}
		}
	}

}
