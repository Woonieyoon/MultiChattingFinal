package server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ServerSender {

	private Socket socket;
	private ExecutorService executorService;
	private List<ServerClient> connections = new ArrayList<ServerClient>();
	private ServerClient serverClient;

	public ServerSender(ServerClient serverClient) {
		this.serverClient = serverClient;
		this.socket = serverClient.getSocket();
		this.executorService = serverClient.getExecutorService();
		this.connections = serverClient.getConnections();
	}

	public synchronized void send(String data) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					DataOutputStream outputStream = new DataOutputStream(
							new BufferedOutputStream(socket.getOutputStream()));
					outputStream.writeUTF(data);
					outputStream.flush();
				} catch (Exception e) {
					System.out.println("[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress());
					connections.remove(serverClient);
					try {
						socket.close();
					} catch (IOException e1) {
					}
				}
			}
		};
		executorService.submit(runnable);
	}


}
