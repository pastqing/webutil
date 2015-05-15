package util.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 服务器监听类
 * @author suqing
 */


public class SocketListener extends Thread{
	
	private int port;
	private ServerSocket server;
	public SocketListener() {
		this.port = 8899;
	}
	
	public SocketListener(int port) {
		this.port = port;
	}
	public void run() {
		try {
			server = new ServerSocket(port);
			while(true) {
				Socket client = server.accept();
				
					System.out.println("a client connected : " + client.getRemoteSocketAddress());
					//Handle
					System.out.println("client inputStream: " + client.isInputShutdown());
					SocketHandle sh =new SocketHandle(client);
					sh.start();
				

			}

			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
