package com.jnanlongen.screenshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ScreenShotThread implements Runnable{  
    private List<String> urlList;  
  
    public ScreenShotThread(List<String> urlList) {  
        this.urlList = urlList;  
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
//            Path path = Paths.get(dirpath); 
              // 2.1 屏幕截图功能
//    			boolean flag = this.doScreenShot(path, url);
//    		    if(flag) {
//    		    	log.info(System.currentTimeMillis()+" #######图片截取成功:######  "+url);
//    		    }else {
//    		    	log.error(System.currentTimeMillis()+" #######图片截取失败:######  "+url);
//    		    }
        	 System.out.println(url);
        	 try {  
                 Thread.sleep(30000); //休眠30毫秒后继续运行 
             } catch (InterruptedException e) {  
                 e.printStackTrace();  
             }  
    		
        }
    	
    	
          
    }  
      
}  
public class ThreadTest {


	public static void main(String[] args) {  
		List<String> list = new ArrayList<String>();
		list.add("11111111");
		list.add("222222222");
		list.add("55555555");
		
        new Thread(new ScreenShotThread(list)).start();  
        //new Thread(new ScreenShotThread(list)).start();  
    }  


}
