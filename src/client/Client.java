package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private String ip;
	private int port;
	private Socket socket;
	private String userName;

	public Client(String IP, int port, String userName) {
		this.ip = IP;
		this.port = port;
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}


	public void init() {
		socket = new Socket();
	}

	public void execute() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					socket.connect(new InetSocketAddress(ip, port));
					System.out.println("[연결 완료: " + socket.getRemoteSocketAddress() + " ]");
					send(userName);
				} catch (IOException e) {
					if (!socket.isClosed()) {
						close();
					}
					return;
				}
				new ClientReceiver(socket).receive();
			}
		};
		thread.start();
	}

	public void send(String data) {
		ClientSender client = new ClientSender(socket);
		client.send(data);
	}

	public void close() {
		System.out.println("[연결끊기:" + socket.getRemoteSocketAddress() + "]");
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		Client client = new Client("localhost", 10020, "홍길동");
		client.init();
		client.execute();

		Scanner sc;
		while(true) {
			sc = new Scanner(System.in);
			sc.reset();
			System.out.print("user1:");
			String data = sc.nextLine();
			if(data.equalsIgnoreCase("Quit")) {
				client.close();
				sc.close();
			}
			client.send(data);
		}
	}

}
