package com.jinanlongen.screenshot.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenshotTestController {
	
	// 日志
    protected Log log = LogFactory.getLog(this.getClass());
	
    /**
     * http://localhost:8081/
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    /**
     * http://localhost:8081/hello
     * @return
     */
    @RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }
    
    @RequestMapping("/testpath")
    public String testpath(HttpServletRequest request) {
    	//String path = GetWebProjectRealPathTool.getRootPath();
    	//String path = request.getRealPath("/"); //获取项目所在服务器的全路径
    	String path = request.getServletContext().getRealPath("/");//获取项目所在服务器的全路径
    	
    	//获取操作系统类型
    	//System.out.println("===========os.name:"+System.getProperties().getProperty("os.name"));  
    	//System.out.println("===========file.separator:"+System.getProperties().getProperty("file.separator"));
    	String servername = System.getProperties().getProperty("os.name");
    	path = "项目路径：\n"+path+ "\n 服务器类型：\n"+servername;
        return path;
    }
    
    @RequestMapping("/filetest")
    public String filetest() {
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
        	System.out.println("文件已经存在，输出文件的相关信息");
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
            System.out.println(f.length());
        } else {
          //  先创建文件所在的目录
            f.getParentFile().mkdirs();
            try {
             // 创建新文件
                f.createNewFile();
                System.out.println("创建新文件完成"+f.getPath());
            } catch (IOException e) {
                System.out.println("创建新文件时出现了错误。。。");
                e.printStackTrace();
            }
        }
        return "success: \n"+f.getPath();
    }
    
    
  
    
}
