package snapshot.tasktest;

import java.util.Timer;

import org.junit.Test;

import snapshot.model.Page;
import snapshot.task.SnapshotProcessorDeamon;
import snapshot.task.TaskQueue;
//屏幕截图工具类
public class TestScreenShotQueueUtil {
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

    
    /**
     * @param args    
     */
        @Test
    public void testSreenShot() {
        long start = System.currentTimeMillis();
        System.out.println("开始时间：" + start);       
        
        TestScreenShotQueueUtil.setBefore(System.currentTimeMillis()+"", "http://www.baidu.com/");
        TestScreenShotQueueUtil.setRun("c:/snapshot/");
        
        long end = System.currentTimeMillis();
        System.out.println("结束时间：" + end);
        System.out.println("花费时间：" + (end - start) / 1000 + "秒");
    }
}