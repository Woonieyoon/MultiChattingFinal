package server;

import java.net.Socket;

public interface SplitedTokenProcessor {
	public void Process(Socket socket, String data);
}
