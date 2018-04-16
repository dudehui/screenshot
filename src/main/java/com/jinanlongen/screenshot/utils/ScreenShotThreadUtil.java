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
public class ScreenShotThreadUtil implements Runnable{  
	// 日志
    protected Log log = LogFactory.getLog(this.getClass());
    private List<String> urlList;  
    private String dirpath;
  
    public ScreenShotThreadUtil(List<String> urlList, String dirpath) {  
        this.urlList = urlList;  
        this.dirpath = dirpath;
    }  
  
    @Override  
    public void run() {  
    	
    	 // 2.2 依次取出集合对象，循环执行截图功能,截取一次休眠 30 秒
    	 /**
         * 参数：dir 磁盘路径  dirpath
         *      url 连接地址 url
         *      name name
         */  
        Iterator<String> it=urlList.iterator();
        while(it.hasNext()){
        	log.info("------start screenshot："+"----url: "+it.next());
            //System.out.println(it.next());
            //String url = "https://news.ycombinator.com";
        	String url = it.next();        
        	String name = ScreenShotUtil.getImageName();         
            String path = dirpath; 
               //2.1 屏幕截图功能
            boolean isUrlFlag = ScreenShotUtil.urlCheck(url);
           	log.info("-----------url check result------------:"+isUrlFlag);
           	if(isUrlFlag) {
           		boolean flag = ScreenShotUtil.getCdp4jInstance().doScreenShot(path, url, name);
    		    if(flag) {
    		    	log.info("时间点："+System.currentTimeMillis()+" #######图片截取:"+"success"+" ######  "+url);
    		    }else {
    		    	log.error("时间点："+System.currentTimeMillis()+" #######图片截取:"+"error"+"  ######"+url);
    		    }
    		    log.info("------end screenshot："+"----url:"+it.next());
           	}else {
           		log.info("-----------url check result is false, -----URL格式不正确-------:"+isUrlFlag);
           	}
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
		
        new Thread(new ScreenShotThreadUtil(list, dir)).start();  
        //new Thread(new ScreenShotThread(list, dir)).start();
	}
      
}  
