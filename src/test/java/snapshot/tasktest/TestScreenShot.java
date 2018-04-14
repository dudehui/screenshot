package snapshot.tasktest;

import java.util.Timer;

import org.junit.Test;

import com.jinanlongen.screenshot.utils.ScreenShotUtil;

import snapshot.model.Page;
import snapshot.task.SnapshotProcessorDeamon;
import snapshot.task.TaskQueue;
/**
 * 队列测试屏幕截图
 * @author Administrator
 *
 */
public class TestScreenShot {
    private TaskQueue<Page> queue;
    private Timer timer;

    //入队列
    public void setUp() {
    	String imagename = ScreenShotUtil.getImageName();//图片名,不会重复
        queue = new TaskQueue<Page>();
//        queue.put(new Page("微软", "https://www.microsoft.com/zh-cn/"));
        //queue.put(new Page("MOP", "http://www.mop.com/"));
        //queue.put(new Page("MSN", "http://www.msn.com/"));
        //queue.put(new Page("阿里巴巴", "http://www.alibaba.com/"));
        //queue.put(new Page("淘宝", "http://www.taobao.com/"));
//        queue.put(new Page(imagename, "http://www.baidu.com/"));
        //queue.put(new Page("谷歌", "http://www.google.com/"));
//        queue.put(new Page("腾讯", "http://www.qq.com/"));
//        queue.put(new Page("1", "https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey"));
        queue.put(new Page(imagename, "https://www.finishline.com/store/product/womens-puma-popcat-slide-sandals/prod2772299?styleId=36384702&colorId=002"));
//        queue.put(new Page(imagename, "https://www.zappos.com/p/nike-cotton-cushion-crew-with-moisture-management-3-pair-pack-black-white/product/8068305/color/151"));
    }

    
    public void setRun() {
        timer = new Timer();
        SnapshotProcessorDeamon deamon = new SnapshotProcessorDeamon();
        deamon.setAvgmax(2);//每个线程平均分配的任务数超过该数量则增加新线
        //File f = new File(this.getClass().getResource("/").getPath()); // 获取类加载的根路径 例如：D:/IdeaProjects/cdby_wan/WebRoot/WEB-INF/classes
        //deamon.setExec(f.toString().replaceAll("\\\\","/")+"/IECapt.exe");
        //deamon.setExec("c:\\IECapt.exe");
        deamon.setMax(5);//线程最大数
        deamon.setImageRoot("c:/snapshot/");//----参数1：图片目录
        deamon.setTaskQueue(queue);//-------------参数2：将队列加入任务线程
        deamon.setTime(1000l);//寻找图片间隔时间
        deamon.setTimeout(20000l);//寻找图片超时时间
        timer.schedule(deamon, 1000, 10000);//大意是在延时delay毫秒后重复的执行task，周期是period毫秒。
        do {
            try {
                Thread.sleep(11000l);//线程休眠11秒
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
        TestScreenShot t = new TestScreenShot();
        t.setUp();
        t.setRun();
        long end = System.currentTimeMillis();
        System.out.println("结束时间：" + end);
        System.out.println("花费时间：" + (end - start) / 1000 + "秒");
    }
    
}