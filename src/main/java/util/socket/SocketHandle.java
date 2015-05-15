package util.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketHandle extends Thread {
	private Socket client;
	
	public SocketHandle(Socket client) {
		this.client = client;
	}
	
	public void run() {
		System.out.println("Handle run");
		try {
			LengthFrame lengthFrame = new LengthFrame(
				new DataInputStream(client.getInputStream()));
				while(true) {
					System.out.println("socketfd is closed: " + client.isConnected()); 
					System.out.println("inputStreawm is closed: " + client.isConnected()); 
					if(client.isClosed()){
						break;
					}
					byte[] msg = lengthFrame.nextMsg();
					System.out.println("Client msg: " + new String(msg));
					lengthFrame.frameMsg(msg, client.getOutputStream());

				}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
