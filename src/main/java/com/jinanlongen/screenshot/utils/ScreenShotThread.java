package com.jinanlongen.screenshot.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 屏幕截图的线程
 * @author dudh
 *
 */
public class ScreenShotThread implements Runnable{  
	// 日志
    protected Log log = LogFactory.getLog(this.getClass());
    private List<String> urlList;  
    private String dirpath;
  
    public ScreenShotThread(List<String> urlList, String dirpath) {  
        this.urlList = urlList;  
        this.dirpath = dirpath;
    }  
  
    @Override  
    public void run() {  
    	
    	 // 2.2 依次取出集合对象，循环执行截图功能,截取一次休眠 30 秒
        Iterator<String> it=urlList.iterator();
        while(it.hasNext()){
            //System.out.println(it.next());
            //String url = "https://news.ycombinator.com";
        	String url = it.next();        
        	 /**
             * 参数：dir 磁盘路径  dirpath
             *      url 连接地址 url
             */           
            Path path = Paths.get(dirpath); 
               //2.1 屏幕截图功能
    			boolean flag = ScreenShotQueueUtil.doScreenShot(path, url);
    		    if(flag) {
    		    	log.info(System.currentTimeMillis()+" #######图片截取成功:######  "+url);
    		    }else {
    		    	log.error(System.currentTimeMillis()+" #######图片截取失败:######  "+url);
    		    }
        	 System.out.println(url);
        	 try {  
                 Thread.sleep(10000); //休眠30毫秒后继续运行 
             } catch (InterruptedException e) {  
                 e.printStackTrace();  
             }  
    		
        }
    	
    	
          
    }  
    
    
    public static void main(String[] args) {
    	List<String> list = new ArrayList<String>();
    	//磁盘位置
    	String dir = "c:/snapshot1/";
		list.add("https://www.baidu.com");
		list.add("https://www.zappos.com/p/nike-cotton-cushion-crew-with-moisture-management-3-pair-pack-black-white/product/8068305/color/151");
		list.add("https://support.mozilla.org/zh-CN/kb/Firefox%20%E4%BD%BF%E7%94%A8%E5%85%A5%E9%97%A8");
		
        new Thread(new ScreenShotThread(list, dir)).start();  
        //new Thread(new ScreenShotThread(list, dir)).start();
	}
      
}  
