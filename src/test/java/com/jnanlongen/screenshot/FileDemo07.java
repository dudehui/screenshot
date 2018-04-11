package com.jnanlongen.screenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class FileDemo07 {
	
	public static void main(String args[]){
		File f = new File("d:"+File.separator+"mldn") ;// 实例化File类的对象
		f.mkdir() ; // 创建文件夹
	}
	
	@Test
	public void testfile() throws IOException {
		
	    String strPath = "E:\\a\\aa\\aaa.txt";  
	    File file = new File(strPath);  
	    File fileParent = file.getParentFile();  
	    if(!fileParent.exists()){  
	        fileParent.mkdirs();  
	    }  
	    file.createNewFile();

	}
	
	@Test
	public void testdate() {
		
		
//		System.out.println(new Date());
		//使用Date
		Date d = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println("当前时间：" + sdf.format(d));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		System.out.println("当前时间：" + sdf.format(d));
		
	}
}
