package com.jinanlongen.screenshot.web;

import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jinanlongen.screenshot.utils.Cdp4jUtil;
import com.jinanlongen.screenshot.utils.FileReadUtil;
import com.jinanlongen.screenshot.utils.ScreenShotThreadUtil;
import com.jinanlongen.screenshot.utils.ScreenShotUtil;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import snapshot.task.TaskQueueUtil;

@RestController
public class ScreenshotController {
	
	// 日志
    protected Log log = LogFactory.getLog(this.getClass());
	 
    /**JAVA，测试 cd4j 屏幕截图，一张图片
     * 测试网址：
     * http://www.mop.com/
	   ######################################测试：http://localhost:8081/test
     * @return
     */
    @RequestMapping("/testScreenshot")
    @ResponseBody
    public String screenshot(HttpServletRequest request) {
    	long start = System.currentTimeMillis();
        System.out.println("开始时间：" + start);       
        Launcher launcher = new Launcher();	
        //-------------按照日期来创建dir
        //String rootDir = request.getServletContext().getRealPath("/");//获取项目所在服务器的全路径
        String rootDir = "pic_screenshot";//服务器同级目录下的XXX
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dirname = sdf.format(new Date());//文件名使用 日期
        
        String dirpath = rootDir + File.separator + dirname;
        File rootFile = new File(dirpath); 
        if(!rootFile.exists()){  //如果图片需要的磁盘目录不存在就创建这个目录
        	rootFile.mkdirs();  
	    }  
        //-------------按照日期来创建dir
        //图片存放在 磁盘位置 服务器根目录下，按照日期区分， 文件夹名例如 2018-04-10-18-23-01
        //C:\Users\Administrator\AppData\Local\Temp\tomcat-docbase.4731566088696246055.9081 下的 2018-04-10-18-23-01
        //程序 当前路径下的 XXXX
        Path path = Paths.get(dirpath);
        //Path path = Paths.get("c:\\snapshot1");//磁盘位置 String dir = "c:/snapshot1/";
        
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
        return "花费时间：" + (end - start) / 1000 + "秒" + ", 磁盘路径："+path;
    }
	
    
    /**JAVA 多线程，测试 cd4j 屏幕截图
     * 测试网址：
     * 读取 file.txt 中的url并截图
	   ######################################测试：http://localhost:8081/test
	   分析：多线程不可以采用，一台电脑只有一个浏览器，不能使用多线程，用队列
     * @return
     */
    @RequestMapping("/testScreenshotByThread")
    @ResponseBody
    public String screenshotByFile(HttpServletRequest request) {
    	long start = System.currentTimeMillis();
        System.out.println("开始时间：" + start);  
        //-------1.0------按照日期来创建dir
        //String rootDir = request.getServletContext().getRealPath("/");//获取项目所在服务器的全路径
        String rootDir = "pic_screenshot";//服务器同级目录下的XXX
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dirname = sdf.format(new Date());//磁盘文件名使用 日期
        
        String dirpath = rootDir + File.separator + dirname;//构造的磁盘路径
        File rootFile = new File(dirpath); 
        if(!rootFile.exists()){  //如果图片需要的磁盘目录不存在就创建这个目录
        	rootFile.mkdirs();  
	    }  
        //-------------按照日期来创建dir
        //图片存放在 磁盘位置 服务器根目录下，按照日期区分， 文件夹名例如 2018-04-10-18-23-01
        //C:\Users\Administrator\AppData\Local\Temp\tomcat-docbase.4731566088696246055.9081 下的 2018-04-10-18-23-01
        //程序 当前路径下的 XXXX
        
        //Path path = Paths.get("c:\\snapshot1");//磁盘位置 String dir = "c:/snapshot1/";
        
        //-----2.0----调用谷歌浏览器截图--------------------------------
        // 2.1 读取 file.txt中的url 放到  线程池、JAV集合中       
           // 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
        String directory = "url" + separator + "data.txt";//url文件所在的目录
        
        Map<String, List<String>> map = FileReadUtil.readFileByLines(directory);        
//        ArrayList<String> zapposList= (ArrayList<String>) map.get("zappos"); 
//        ArrayList<String> finishlineList= (ArrayList<String>) map.get("finishline"); 
//        ArrayList<String> eastbayList= (ArrayList<String>) map.get("eastbay"); 
      
        ArrayList<String> allList= (ArrayList<String>) map.get("allurlList"); 
        
        // 2.2 创建线程，依次取出集合对象，循环执行截图功能, 截取一个休眠 30秒 
              //####### 根据需要对网站url 进行分类依次创建多个线程对象处理请求 
        
        new Thread(new ScreenShotThreadUtil(allList, dirpath)).start();  //allurlList 
        
//        new Thread(new ScreenShotThread(zapposList, dirpath)).start();  //zappos 
//        new Thread(new ScreenShotThread(finishlineList, dirpath)).start();  //站点A的url finishline
//        new Thread(new ScreenShotThread(eastbayList, dirpath)).start();  //......... eastbay
        
//        Iterator<String> it=urlList.iterator();
//        while(it.hasNext()){
//            //System.out.println(it.next());
//            //String url = "https://news.ycombinator.com";
//        	String url = it.next();
//        	 /**
//             * 参数：dir 磁盘路径  dirpath
//             *      url 连接地址 url
//             */           
//            Path path = Paths.get(dirpath); 
//              // 2.1 屏幕截图功能
//    			boolean flag = ScreenshotUtil.doScreenShot(path, url);
//    		    if(flag) {
//    		    	log.info(System.currentTimeMillis()+" #######图片截取成功:######  "+url);
//    		    }else {
//    		    	log.error(System.currentTimeMillis()+" #######图片截取失败:######  "+url);
//    		    }
//    		
//        }
        
       
        long end = System.currentTimeMillis();
        System.out.println("结束时间：" + end);
        System.out.println("花费时间：" + (end - start) / 1000 + "秒");
        return "花费时间：" + (end - start) / 1000 + "秒" + ", 磁盘路径："+dirpath;
    }

    
    
    /**JAVA多线程+队列，测试 cd4j 屏幕截图
     * 测试网址：
     * 读取 file.txt 中的url并截图
	   ######################################测试：http://localhost:8081/test
	   分析：一台电脑只有一个浏览器，不能使用单独的多线程，用队列+多线程
     * @return
     */
    @RequestMapping("/testScreenshotByQueue")
    @ResponseBody
    public String screenshotByQueue(HttpServletRequest request) {
    	long start = System.currentTimeMillis();
        System.out.println("开始时间：" + start);  
        //-------1.0------按照日期来创建dir
        //String rootDir = request.getServletContext().getRealPath("/");//获取项目所在服务器的全路径
        String rootDir = "pic_screenshot";//服务器同级目录下的XXX
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dirname = sdf.format(new Date());//磁盘文件名使用 日期
        
        String dirpath = rootDir + File.separator + dirname;//构造的磁盘路径
        File rootFile = new File(dirpath); 
        if(!rootFile.exists()){  //如果图片需要的磁盘目录不存在就创建这个目录
        	rootFile.mkdirs();  
	    }  
        //-------------按照日期来创建dir
        //图片存放在 磁盘位置 服务器根目录下，按照日期区分， 文件夹名例如 2018-04-10-18-23-01
        //C:\Users\Administrator\AppData\Local\Temp\tomcat-docbase.4731566088696246055.9081 下的 2018-04-10-18-23-01
        //程序 当前路径下的 XXXX
        
        //Path path = Paths.get("c:\\snapshot1");//磁盘位置 String dir = "c:/snapshot1/";
        
        //-----2.0----调用谷歌浏览器截图--------------------------------
        // 2.1 读取 file.txt中的url 放到  队列线程池、JAVA集合中       
           // 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
        String directory = "url" + separator + "file.txt";//url文件所在的目录
        
        Map<String, List<String>> map = FileReadUtil.readFileByLines(directory);        

        ArrayList<String> allurlList= (ArrayList<String>) map.get("allurlList"); 
        
        // 2.2 创建线程，依次取出集合对象，循环执行截图功能, 截取一个休眠 30秒 
              //####### 根据需要对网站url 进行分类依次创建多个线程对象处理请求   
       //################################################ 
      Iterator<String> it=allurlList.iterator();
      while(it.hasNext()){//入队        
      	String url = it.next();
      	String name = ScreenShotUtil.getImageName();
      	TaskQueueUtil.setBefore(name, url);
      }        
             
       //TaskQueueUtil.setRun("c:/snapshot/");//截图位置
      TaskQueueUtil.setRun(dirpath);//截图
      //################################################## 
        long end = System.currentTimeMillis();
        System.out.println("结束时间：" + end);
        System.out.println("花费时间：" + (end - start) / 1000 + "秒");
        return "花费时间：" + (end - start) / 1000 + "秒" + ", 磁盘路径："+dirpath;
    }
    
}