package util.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 基于定界符的成帧
 * 1、添加成帧消息
 * 2、读取成帧消息
 * @author suqing
 *
 */
public class DelimeFrame implements Frame{
	
	public static final byte DELIMER = '\n'; //以\n为定界符
	private InputStream in;
	public DelimeFrame(InputStream in) {
		this.in = in;
	}
	
	public void frameMsg(byte[] message, OutputStream out) throws IOException {
		for(byte b : message ) {
			if( b == DELIMER) {
				//添加填充算法
				throw new IOException("message contain delimer");
			}
		}
		out.write(message);
		out.write(DELIMER);
		out.flush();
	}

	public byte[] nextMsg() throws IOException {
		ByteArrayOutputStream byteArrayMessage = new ByteArrayOutputStream();
		int resRead = 0;
		while((resRead = in.read()) != DELIMER) {
			if( -1 == resRead ) {
				if(byteArrayMessage.size() == 0) {
					return null;
				}else
					throw new IOException("message no delimer");
			}
			byteArrayMessage.write(resRead);
		}
		return byteArrayMessage.toByteArray();
	}

}
