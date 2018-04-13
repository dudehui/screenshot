package com.jnanlongen.screenshot;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

public class FileTest {

	
	public static void main(String[] args) {
        // 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
        String directory = "myDir1" + separator + "myDir2";
        // 以下这句的效果等同于上面两句，windows下正斜杠/和反斜杠都是可以的
// linux下只认正斜杠，为了保证跨平台性，不建议使用反斜杠（在java程序中是<a href="https://www.baidu.com/s?wd=%E8%BD%AC%E4%B9%89%E5%AD%97%E7%AC%A6&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1d9uW64njRkPH-bP1wWPv7-0ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3En1cdn1fvPjck" target="_blank" class="baidu-highlight">转义字符</a>，用\来表示反斜杠）
        // String directory = "myDir1/myDir2";
        String fileName = "myFile.txt";
        // 在内存中创建一个文件对象，注意：此时还没有在硬盘对应目录下创建实实在在的文件
        File f = new File(directory,fileName);
        if(f.exists()) {
          // 文件已经存在，输出文件的相关信息
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
            System.out.println(f.length());
        } else {
          //  先创建文件所在的目录
            f.getParentFile().mkdirs();
            try {
             // 创建新文件
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("创建新文件时出现了错误。。。");
                e.printStackTrace();
            }
        }
        
    }
	
	
	@Test
	public void testNumber() {
	    long start = System.currentTimeMillis();  
        Random rd = new Random();  
        String[] radmon = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };  
        // for(int j = 0; j < 100; j++) {  
        StringBuffer sb = new StringBuffer();  
  
        for (int i = 0; i < 9; i++) {  
            String s = radmon[rd.nextInt(10)];  //0-9的随机数
            sb.append(s);  
        }  
        System.out.println(sb);  
        // System.out.println((int)((Math.random()*9+1)*100000));  
        // }  
        long end = System.currentTimeMillis();  
        //System.out.println(end - start + ";start:" + start + ";end:" + end);  
        //System.exit(0);  
        /* 
         * 952127393 
         * 0;start:1491460840965;end:1491460840965 
         * */  
	}
 
	
}
