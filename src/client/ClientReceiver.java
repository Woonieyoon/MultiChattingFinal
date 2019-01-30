package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceiver {

	private Socket socket;

	public ClientReceiver(Socket socket) {
		this.socket = socket;
	}

	public void receive() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						DataInputStream inputStream = new DataInputStream(
								new BufferedInputStream(socket.getInputStream()));
						String data = inputStream.readUTF();
						System.out.println(data);

					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("서버 통신 안됨");
						break;
					}
				}
			}
		};
		thread.start();

	}

}
