package com.jinanlongen.screenshot;

import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

@RestController
public class ScreenshotController {
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
        return "Hello world";
    }
    
    
    /**JAVA多线程队列，测试 cd4j 屏幕截图
     * 测试网址：
     * http://www.mop.com/
######################################测试：http://localhost:8081/test
     * @return
     */
    @RequestMapping("/test")
    public String screenshot(HttpServletRequest request) {
    	long start = System.currentTimeMillis();
        System.out.println("开始时间：" + start);       
        Launcher launcher = new Launcher();		
		//Path path = Paths.get("c:\\snapshot1");//磁盘位置 String dir = "c:/snapshot1/";
        
        //-------------按照日期来创建dir
        String rootDir = request.getServletContext().getRealPath("/");//获取项目所在服务器的全路径
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dirname = sdf.format(new Date());//文件名使用 日期
        
        String dirpath = rootDir + File.separator + dirname;
        File rootFile = new File(dirpath); 
        if(!rootFile.exists()){  //如果不存在就创建这个目录
        	rootFile.mkdirs();  
	    }  
        //-------------按照日期来创建dir
        Path path = Paths.get(dirpath);//图片存放在 磁盘位置 服务器根目录下，按照日期区分， 文件夹名例如2018-04-10-18-23-01
        
		String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名
		Path file = null;		
		try {
			file = createTempFile(path, "screenshot-"+ts, ".png");
			 try (        		
			        	SessionFactory factory = launcher.launch();
			            Session session = factory.create()) 
			 {
			            session.navigate("https://news.ycombinator.com");
			            session.waitDocumentReady();
			            // activate the tab/session before capturing the screenshot
			            session.activate();
			            byte[] data = session.captureScreenshot();
			            write(file, data);
			  }
		} catch (Exception e) {
			// io exception
		}		
       
        
        long end = System.currentTimeMillis();
        System.out.println("结束时间：" + end);
        System.out.println("花费时间：" + (end - start) / 1000 + "秒");
        return "花费时间：" + (end - start) / 1000 + "秒";
    }
	
    
    
    
}
