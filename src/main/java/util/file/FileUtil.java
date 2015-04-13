package util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * 文件的相关操作
 * 创建， 删除， 复制， 读， 写
 * @author su
 *
 */
public class FileUtil extends ArrayList<String>{

	/**
	 * 创建一个文件
	 * @param pathName
	 * @throws Exception
	 */
	public static void createFile(String pathName) throws Exception {
		if( null == pathName ||
			"".equals(pathName)) {
			throw new Exception("文件名无效");
		}
		boolean res = false;
		File file = new File(pathName);
		if( file.exists()) {
			throw new Exception("文件已存在");
		}else
			file.createNewFile();
	}
	/**
	 * 读取文件内容
	 * @param fileName
	 * @return
	 */
	public static String read(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(
					new FileReader( 
							new File(fileName).getAbsoluteFile()));
			try {
				String s;
				while((s = br.readLine())!= null) {
					sb.append(s);
					sb.append("\n");
				}
			}finally{
				br.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
		
	}
	/**
	 * 向文件中写入数据
	 * @param fileName
	 * @param text
	 */
	public static void write(String fileName, String text) {
		try {
			PrintWriter pw = new PrintWriter(
					new File(fileName).getAbsoluteFile());
			try{
				pw.print(text);
			}finally{
           				pw.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构造函数，spliter为分界符
	 * @param fileName
	 * @param spliter
	 */
	public FileUtil(String fileName, String spliter) {
		super(Arrays.asList(read(fileName).split(spliter)));
		if( "".equals(get(0)) ) {
			remove(0);
		}
	}
	/**
	 * 默认用换行符来分割
	 * @param fileName
	 */
	public FileUtil(String fileName) {
		this(fileName, "\n");
	}
	
	
	
	public static void main(String[] args) throws Exception {
		TreeSet<String> s = new TreeSet<String>(
				new FileUtil("pom.xml"));
		System.out.println(s.headSet("a"));
	}

}
