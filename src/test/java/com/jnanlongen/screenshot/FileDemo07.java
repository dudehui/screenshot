package com.jnanlongen.screenshot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
		
     //System.out.println(new Date());
		//使用Date
		Date d = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println("当前时间：" + sdf.format(d));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		System.out.println("当前时间：" + sdf.format(d));
		
	}
	
	
	
	
	@Test
	public void readFile() {
		
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  

            /* 读入TXT文件 */  
            String pathname = "D:\\twitter\\13_9_6\\dataset\\en\\input.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
            File filename = new File(pathname); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line = "";  
            line = br.readLine();  
            while (line != null) {  
                line = br.readLine(); // 一次读入一行数据  
            }  

            /* 写入Txt文件 */  
            File writename = new File(".\\result\\en\\output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
            writename.createNewFile(); // 创建新文件  
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
            out.write("我会写入文件啦\r\n"); // \r\n即为换行  
            out.flush(); // 把缓存区内容压入文件  
            out.close(); // 最后记得关闭文件  

        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
	}
	
	
	
}
