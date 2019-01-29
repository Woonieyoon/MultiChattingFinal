package client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientSender {

	Socket socket;

	public ClientSender(Socket socket) {
		this.socket = socket;
	}

	public void send(String data) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					DataOutputStream outputstream = new DataOutputStream(
							new BufferedOutputStream(socket.getOutputStream()));
					outputstream.writeUTF(data);
					outputstream.flush();
					// System.out.println("[전송 완료]");
				} catch (Exception e) {
					System.out.println("서버 통신 안됨");
				}
			}
		};
		thread.start();
	}

}
