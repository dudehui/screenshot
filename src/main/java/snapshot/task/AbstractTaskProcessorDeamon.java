package snapshot.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * 该类负责分析任务处理队列长度增加和减少任务处理线
 */
public abstract class AbstractTaskProcessorDeamon<T> extends TimerTask {
    // 日志
    protected Log log = LogFactory.getLog(this.getClass());
    // 任务队列
    protected TaskQueue<T> taskQueue;
    // 现有任务处理线程
    protected List<Thread> processors = new ArrayList<Thread>();
    // 每个线程平均分配的任务数超过该数量则增加新线?
    private Integer avgmax;
    // 线程最大数
    private Integer max;

    //构???方法初始化任务处理线程集合
    public AbstractTaskProcessorDeamon() {
        this.processors = new ArrayList<Thread>();
    }

    /* * (non-Javadoc) * @see java.util.TimerTask#run() */
    @Override
    public void run() {
        this.analyzeTaskQueue();
    }

    /**
     * 创建新的任务处理线程 * @return 新的任务处理线程
     */
    public abstract Thread createProcessor();

    /**
     * 分析队列长度加入或减少快照线??
     */
    protected void analyzeTaskQueue() {
        Integer value = this.taskQueue.size() > 0 && this.taskQueue.size() < avgmax ? value = 1 - this.processors.size() : this.taskQueue.size() / avgmax - this.processors.size();
        if (value > 0 && this.processors.size() < max) {
            for (int i = 0; i < value; i++) {
                this.processors.add(this.createProcessor());
                log.debug("Added 1 thread, " + this.processors.size() + " running.");
            }
        } else if (value < 0) {
            for (int i = 0; i < this.processors.size(); i++) {
                Thread processor = this.processors.get(i);
                if (!processor.isInterrupted() && processor.getState().equals(State.TERMINATED)) {
                    processor.interrupt();
                    this.processors.remove(processor);
                    log.debug("Removed 1 thread, " + this.processors.size() + " running.");
                    if (++value == 0) {
                        break;
                    } else if (i == this.processors.size() - 1) {
                        i = 0;
                    }
                }
            }
        }
    }

    /**
     * 获得任务处理线程数量 * @return 任务处理线程数量
     */
    public int getProcessorCount() {
        return this.processors.size();
    }

    /**
     * @param tasks the tasks to set
     */
    public void setTaskQueue(TaskQueue<T> tasks) {
        this.taskQueue = tasks;
    }

    /**
     * @param max the max to set
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @param avgmax the avgmax to set
     */
    public void setAvgmax(Integer avgmax) {
        this.avgmax = avgmax;
    }
}

