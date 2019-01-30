package server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSender {

	private Socket socket;
	private List<ServerClient> connections = new ArrayList<ServerClient>();
	private ServerClient serverClient;

	public ServerSender(ServerClient serverClient) {
		this.serverClient = serverClient;
		this.socket = serverClient.getSocket();
		this.connections = serverClient.getConnections();
	}

	public void send(String data) {
		try {
			DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			outputStream.writeUTF(data);
			outputStream.flush();
			System.out.println("서버에서 클라이언트로 전송");
		} catch (Exception e) {
			System.out.println("[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress());
			connections.remove(serverClient);
			try {
				socket.close();
			} catch (IOException e1) {
			}
		}
	}


}
