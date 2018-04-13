package snapshot.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 该类负责处从任务队列中取得任务并传递给process方法
 */
public abstract class AbstractTaskProcessor<T> extends Thread {
    // 日志
    protected Log log = LogFactory.getLog(this.getClass());
    // 任务队列
    protected TaskQueue<T> taskQueue;

    /* * 该方法会循环从人物队列中取得新任务传递给process方法 * 取空任务队列后该线程进入停止状态,待守护线程下次执行会释放该线程的资源 * * @see java.lang.Thread#run() */
    public void run() {
        T task = null;
        while ((task = this.taskQueue.pop()) != null) {
            this.process(task);
        }
    }

    /**
     * 每次执行时会得到一个新的任务对象 * @param task
     */
    public abstract void process(T task);

    /**
     * 每次该线程被销毁时候
     */
    public abstract void onDestory();

    /**
     * @param taskQueue * the taskQueue to set
     */
    public void setTaskQueue(TaskQueue<T> taskQueue) {
        this.taskQueue = taskQueue;
    }
}
