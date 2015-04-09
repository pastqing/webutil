##java文件的相关学习
---
###一·File类
#### 重要的API
- **String [] java.io.File.list()**
- **String [] java.io.File.list(FilenameFilter filter)**

**方法简述：**

- **list()方法**: 如果打开的路径不是一个目录， 则返回空（**null**）， 其他情况下返回该目录下的所有文件的名字。

- **list(FilenameFilter filter)方法：**参数为一个FilenameFilter接口， list将会调用该接口中的**accept**方法， 源码如下：

```
	public String[] list(FilenameFilter filter) {
        String names[] = list();
        if ((names == null) || (filter == null)) {
            return names;
        }
        List<String> v = new ArrayList<>();
        for (int i = 0 ; i < names.length ; i++) {
            if (filter.accept(this, names[i])) {
                v.add(names[i]);
            }
        }
        return v.toArray(new String[v.size()]);
    }
```
** java.io.FileNaemFilter.accept **源码：

```
	public interface FilenameFilter{
		boolean accept(File dir, String name);
	}
```
**accept**根据传入的File引用和过滤规则（文件名）来判断是否存在相应的文件
**list(FilenameFilter filter)**从源码中可以看出该方法返回的是调用过滤后的文件名
下面是目录过滤器的Demo：

```
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
}
```
这里需要注意两点：

- **list()**可以接受任何实现了**FilenameFilte**接口的类的对象， 这是一种策略模式。
- **list(final String regex)**关键字用到了**final**因为这在实现**匿名内部类**中是必须的， 这样才能使用来自该类范围之外的对象。

**tips**： **File(".")**的路径是当前工作路径

---
要读取多个文件， 可以用以下API

- ** File[] java.io.File.listFiles() **
- ** File[] java.io.File.listFiles(FilenameFilter) **

**listFiles**的用法与上面提到的**list**的用法基本相同，下面给出**Think In Java**中的一个demo

```
	public static File[] local(File dir, String regex) {
		return dir.listFiles(
			new FilenameFilter() {
				private Pattern pattern = Pattern.compile(regex);
				public boolean accept(File dir, String name) {
					return pattern.matcher(new File(name).getName()).matches();
			}
		});
	}
	
	public static class TreeInfo implements Iterable<File> {		public List<File> files = new ArrayList<File>();
		public List<File> dirs  = new ArrayList<File>();
		...
	}

	static TreeInfo recurseDirs(File startDir, String regex){
		
	}
	public static TreeInfo walk(File dir, String regex) {}
	
```

- **local**方法通过使用**匿名内部类FilenameFilter**的方法来产生**File**的数组
- 类**TreeInfo**的作用是来收集要遍历的**File**数组，已为以后的需求所服务（例如打印文件名）， 注意这里的小技巧， **TreeInfo**类实现了**Iterable<File>**接口， 意思就是说返回了一个**File**迭代器， 使你拥有在文件列表上的默认迭代， 这样可以就利用迭代器的相关特性了。例如**foreach**遍历

- **recurseDirs**用来遍历所匹配的目录
- **walk**方法用来得到想要遍历的目录信息。同时可以利用**walk**方法来进一步的对目录信息进行处理。




