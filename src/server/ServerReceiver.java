package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ServerReceiver {

	private Socket socket;
	private ExecutorService executorService;
	private List<ServerClient> connections = new ArrayList<ServerClient>();
	private ServerClient serverClient;
	private RequestManager requestManager;
	private boolean userNameInit = true;

	public ServerReceiver(ServerClient serverClient) {
		this.serverClient = serverClient;
		this.socket = serverClient.getSocket();
		this.executorService = serverClient.getExecutorService();
		this.connections = serverClient.getConnections();
		requestManager = new RequestManager(serverClient);
	}

	public synchronized void receive() {
		System.out.println("서버 리시브");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						DataInputStream inputStream = new DataInputStream(
								new BufferedInputStream(socket.getInputStream()));
						String data = inputStream.readUTF();
						
						String message = "[요청 처리: " + socket.getRemoteSocketAddress() + " ]" + " / "
								+ Thread.currentThread().getName();
						System.out.println(message);

						if (userNameInit) {
							serverClient.setUserName(data);
							System.out.println("초기");
							userNameInit = false;
						}

						String tokendata = data.split(":")[0];
						System.out.println("들어온 토큰:" + tokendata);

						boolean check = requestManager.getMap().containsKey(tokendata);
						System.out.println("확인:" + check);
						if (check) {
							SplitedTokenProcessor processor = requestManager.getMap().get(tokendata);
							processor.Process(socket, data);
						}

					} catch (IOException e) {
						connections.remove(serverClient);
						System.out.println("[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress() + " ]");
						try {
							socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						return;
					}

				}
			}
		};
		executorService.submit(runnable);
	}

}
