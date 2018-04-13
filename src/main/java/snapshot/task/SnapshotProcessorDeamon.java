package snapshot.task;

import snapshot.model.Page;
import snapshot.task.SnapshotProcessor;

/**
 * 快照守护线程，该线程通过任务队列长度加入或减少快照线程
 */
public class SnapshotProcessorDeamon extends AbstractTaskProcessorDeamon<Page> {
    // 调用IECapt.exe的windows指令
    //private String exec;
    // 图片根目录
    private String imageRoot;
    // 寻找图片间隔时间
    private Long time = 1000L;
    // 寻找图片超时时间
    private Long timeout = 10000L;

    /* * (non-Javadoc) * * @see com.futurebnc.myex.taskmgr.task.AbstractTaskProcessorDeamon#createProcessor() */
    public Thread createProcessor() {
        SnapshotProcessor processor = new SnapshotProcessor();
        processor.setTaskQueue(taskQueue);
        //processor.setExec(exec);
        processor.setImageRoot(imageRoot);
        processor.setTime(time);
        processor.setTimeout(timeout);
        processor.start();
        return processor;
    }

    /**
     * @param exec * the exec to set
     */
//    public void setExec(String exec) {
//        this.exec = exec;
//    }

    /**
     * @param imageRoot * the imageRoot to set
     */
    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }

    /**
     * @param time * the time to set
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * @param timeout * the timeout to set
     */
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
