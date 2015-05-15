package util.socket;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 基于长度的成帧
 * @author suqing
 * @version 1.0
 *
 */
public class LengthFrame implements Frame{

	private DataInputStream in;
	private static final int MAXLENGTH = 0xFFFF;	//消息最大长度
	private static final int BYTEMASK  = 0xFF;		//byte掩码
	public LengthFrame(DataInputStream in) {
		this.in = in ;
	}
	
	
	public void frameMsg(byte[] message, OutputStream out) throws IOException {
		if(message.length > MAXLENGTH) {
			throw new IOException("too long message");
		}else{
			out.write( (message.length >> 8) & BYTEMASK );
			out.write(message.length & BYTEMASK);
			out.write(message);
			out.flush();
		}
		
	}

	public byte[] nextMsg() throws IOException {
		int length;
		try{
			length = in.readUnsignedShort();
			System.out.println("length: " + length);
		}catch(EOFException e) {
			return null;
		}
		byte[] msg = new byte[length];
		in.readFully(msg);
		return msg;
	}

}
