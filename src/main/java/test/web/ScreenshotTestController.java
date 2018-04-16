package test.web;

import java.io.File;
import java.io.IOException;
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

import com.jinanlongen.screenshot.utils.FileReadUtil;
import com.jinanlongen.screenshot.utils.ScreenShotUtil;

import test.snapshot.task.TaskQueueUtil;
import test.thread.ScreenShotThreadUtil;

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
        
        // 2.2 创建线程队列，依次取出集合对象，循环执行截图功能, 截取一个休眠 30秒 
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
  
    
    /**JAVA 多线程，测试 cd4j 屏幕截图
     * 测试网址：
     * 读取 file.txt 中的url并截图
	   ######################################测试：http://localhost:8081/test
	   分析：多线程不可以采用，一台电脑只有一个浏览器
     * @return
     */
    @RequestMapping("/testScreenshotByThread")
    @ResponseBody
    public String testScreenshotByThread(HttpServletRequest request) {
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
        String directory = "url" + separator + "file.txt";//url文件所在的目录
        
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
       
       
        long end = System.currentTimeMillis();
        System.out.println("结束时间：" + end);
        System.out.println("花费时间：" + (end - start) / 1000 + "秒");
        return "花费时间：" + (end - start) / 1000 + "秒" + ", 磁盘路径："+dirpath;
    }

   
    
}
