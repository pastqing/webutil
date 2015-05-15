package util.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketClient extends Thread{
	private Socket Client;
	public SocketClient(String host, int port) throws UnknownHostException, IOException {
		Client = new Socket(host, port);
		System.out.println("Connected server: " + Client.getRemoteSocketAddress());
	}
	public SocketClient() throws UnknownHostException, IOException {
		Client = new Socket("localhost", 8899);
		System.out.println("Connected server: " + Client.getRemoteSocketAddress());
	}
	public Socket getClient() {
		return Client;
	}
	public void setClient(Socket client) {
		Client = client;
	}
	public void run() {
		try {
			//从标准输入中读
				
				BufferedReader br = new BufferedReader(
						new InputStreamReader(System.in));
				String line;
				while((line = br.readLine()) != null ) {
					LengthFrame lengthFrame = new LengthFrame(new DataInputStream(
					Client.getInputStream()));
					lengthFrame.frameMsg(line.getBytes(), Client.getOutputStream());
					byte recv[] = lengthFrame.nextMsg();
					System.out.println("recv from server: " + new String(recv));
				}
				Client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		new SocketClient().start();
	}
	
}
