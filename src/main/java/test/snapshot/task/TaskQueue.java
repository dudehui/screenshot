package test.snapshot.task;

import java.util.ArrayList;
import java.util.List;

public class TaskQueue<T> {
    // 存储page对象的列表
    private List<T> array = new ArrayList<T>();
    // 是否已经销毁
    private boolean enabled = true;

    /**
     * 向队列内加入一个页面 * * @param task * 页面对象
     */
    public void put(T task) {
        if (enabled) {
            synchronized (array) {
                array.add(task);
            }
        }
    }

    /**
     * 从队列中取得一个page对象 * * @return page对象
     */
    public T pop() {
        if (enabled) {
            synchronized (array) {
                T task = null;
                if (array.size() > 0) {
                    task = (T) array.get(0);
                    array.remove(0);
                }
                return task;
            }
        }
        return null;
    }

    /**
     * 取得队列长度 * * @return 队列长度
     */
    public Integer size() {
        return array.size();
    }

    /**
     * 销毁该队列 *
     */
    public void destroy() {
        this.enabled = false;
    }
}