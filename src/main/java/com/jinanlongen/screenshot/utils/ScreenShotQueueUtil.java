package com.jinanlongen.screenshot.utils;

import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Timer;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import snapshot.model.Page;
import snapshot.task.SnapshotProcessorDeamon;
import snapshot.task.TaskQueue;

/**屏幕截图工具类
 * @author Administrator
 *
 */
public class ScreenShotQueueUtil {
	public static TaskQueue<Page> queue = new TaskQueue<Page>();//执行队列
    public static Timer timer = new Timer();
	
    public static void setBefore(String name, String url) {        	
        //queue.put(new Page("MSN", "http://www.msn.com/"));       
        queue.put(new Page(name, url));  
    }
    
    public static void setRun(String imageRoot) {       
        SnapshotProcessorDeamon deamon = new SnapshotProcessorDeamon();
        deamon.setAvgmax(5);       
        deamon.setMax(10);
        deamon.setImageRoot(imageRoot);//  
        deamon.setTaskQueue(queue);
        deamon.setTime(1000l);
        deamon.setTimeout(20000l);
        timer.schedule(deamon, 10000, 10000);
        do {
            try {
                Thread.sleep(11000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (deamon.getProcessorCount() > 0);
    }
	
	 /** 屏幕截图功能
	 * @param path
	 * @param url
	 * @return
	 */
	public static boolean doScreenShot(Path path, String url) {
			Launcher launcher = new Launcher();	
			String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名,不会重复
			Path file = null;		
			try {
				file = createTempFile(path, "screenshot-"+ts, ".png");
				 try (        		
				        	SessionFactory factory = launcher.launch();
				            Session session = factory.create()) 
				  {
				            session.navigate(url);
				            session.waitDocumentReady();
				            // activate the tab/session before capturing the screenshot
				            session.activate();
				            byte[] data = session.captureScreenshot();
				            write(file, data);
				  }
			} catch (Exception e) {	// io exception			
				return false;
			}
			return true;
			
		}

	
	
	 /** 屏幕截图功能
		 * @param dirpath 图片存放的根目录
		 * @param url 超链接地址
		 * @return
		 */
		public static boolean doScreenShot(String dirpath, String imageurl, String imagename) {
			    Path path = Paths.get(dirpath); 
				Launcher launcher = new Launcher();	
				//String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名,不会重复
				Path file = null;		
				try {
					file = createTempFile(path, imagename, ".png");//路径， 名称， 格式
					 try (        		
					        	SessionFactory factory = launcher.launch();
					            Session session = factory.create()) 
					  {
					            session.navigate(imageurl);//超链接
					            session.waitDocumentReady();
					            // activate the tab/session before capturing the screenshot
					            session.activate();
					            byte[] data = session.captureScreenshot();
					            write(file, data);
					  }
				} catch (Exception e) {	// io exception			
					return false;
				}
				return true;
				
			}
	
		
		/**生成图片名称的随机数： 时间戳+9伪随机数
		 * @return
		 */
		public static String getImageName() {
			
			String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名,不会重复
			 Random rd = new Random();  
		     String[] radmon = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };  
		     // for(int j = 0; j < 100; j++) {  
		     StringBuffer sb = new StringBuffer();  
		       for (int i = 0; i < 9; i++) {  
		            String s = radmon[rd.nextInt(10)];  //0-9的随机数
		            sb.append(s);  
		        }  
			return ts+"-"+sb;
		}
		
}
