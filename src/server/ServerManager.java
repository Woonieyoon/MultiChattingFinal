package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.room.RoomManager;

public class ServerManager {
	private List<ServerClient> connections = new ArrayList<ServerClient>();
	private ExecutorService executorService;
	private ServerSocket serverSocket;
	private RoomManager roomManager;

	public ServerManager(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void init() {
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		roomManager = new RoomManager();
		roomManager.createRoom("All");
	}

	public void execute() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("[서버 시작]");

				while (true) {
					try {
						Socket socket = serverSocket.accept();
						String message = "[연결 수락: " + socket.getRemoteSocketAddress() + " ]" + "  /  "
								+ Thread.currentThread().getName();
						System.out.println(message);
						ServerClient client = new ServerClient(socket, executorService, connections , roomManager);
						connections.add(client);
						System.out.println("[클라이언트 접속자수: " + connections.size() + "]");
					} catch (IOException e) {
						if (!serverSocket.isClosed()) {
							close();
						}
						break;
					}
				}
			}
		};
		executorService.submit(runnable);
	}

	public void close() {
		try {
			Iterator<ServerClient> iterator = connections.iterator();
			while (iterator.hasNext()) {
				ServerClient client = iterator.next();
				client.close();
				iterator.remove();
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			if (executorService != null && !executorService.isShutdown()) {
				executorService.shutdown();
			}
			System.out.println("[서버 종료]");
		} catch (Exception e) {
		}
	}

}
