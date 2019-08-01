package thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 定期线程循环执行任务
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/12 15:20
 */
public class MyTaskRunnable implements Runnable{
    private int maxSize;
    private List<MyTask> taskList;

    public MyTaskRunnable(int maxSize) {// 直接定义最大容量
        this.maxSize = maxSize;
        taskList = new ArrayList<>();
    }

    public void add(MyTask myTask) throws Exception {
        if (taskList.size()<maxSize){
            taskList.add(myTask);
        }else {
            throw new Exception("数量过多，不能再处理");
        }
    }
    @Override
    public void run() {
        for (int i = 0; i < taskList.size(); i++) {
            MyTask myTask = taskList.get(i);
            if(myTask.running()){
                myTask.doThis();
            }else {
                taskList.remove(i);
            }
        }
    }
}
