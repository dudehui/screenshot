package snapshot.task;

import java.util.Random;
import java.util.Timer;

import snapshot.model.Page;

/**屏幕截图队列工具类
 * @author Administrator
 *
 */
public class TaskQueueUtil {
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
		
}
