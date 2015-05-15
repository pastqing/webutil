package util.socket;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 成帧接口
 * @author suqing
 * @version 1.0
 */
public interface Frame {
	public void frameMsg(byte[] message, OutputStream out) throws IOException;
	public byte[] nextMsg() throws IOException;
}
