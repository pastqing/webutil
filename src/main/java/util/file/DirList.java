package util.file;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;
public class DirList {
	
	public static String[] list(File path) {
		String[] list = path.list();
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		return list;
	}
	
	public static String[] list(File path, final String regex) {
		//匿名内部类
		String[] list = path.list(new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);
			public boolean accept(File dir, String name){
				return pattern.matcher(name).matches();
			}
		});
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		return list;
	}
	
	
	public static void main(String[] args) {
		for( String s : list(new File(".")) ) {
			System.out.println(s);
		}
//		for(String s : list(new File("/usr/local/apache-tomcat-7.0.57/me-webapps/EducationCloud"), ".*\\.jsp$")) {
//			System.out.println(s);
//		}
	}
}
