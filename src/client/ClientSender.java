package client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSender {

	Socket socket;

	public ClientSender(Socket socket) {
		this.socket = socket;
	}

	public void send(String userName) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try(Scanner scan = new Scanner(System.in);) {
					DataOutputStream outputstream = new DataOutputStream(
							new BufferedOutputStream(socket.getOutputStream()));
					outputstream.writeUTF(userName);
					outputstream.flush();
					while (true) {
						String content = scan.nextLine();						
						outputstream.writeUTF(content);
						outputstream.flush();
					}
					
				} catch (Exception e) {
					System.out.println("서버 통신 안됨");
				}
			}
		};
		thread.start();
	}

}
