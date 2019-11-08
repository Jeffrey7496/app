package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Runnable线程池--每个runnable只能一个线程执行（否则可能一个定时任务被执行多次）
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/12 15:48
 */
public class MyTaskRunnableThreadPool {
    // runnable池
    public static List<MyTaskRunnable> runnablePool= null;

    private static int sineNo = 0;// 记录添加任务的runnable

    private static class LazyHolder{
        // 3个线程 3个runnable，每个runnable 最多2个task
        private static final MyTaskRunnableThreadPool INSTANCE =
                new MyTaskRunnableThreadPool(3,2);
    }

    public static final MyTaskRunnableThreadPool getInstance(){
        return LazyHolder.INSTANCE;
    }

    private MyTaskRunnableThreadPool(int threadNum, int taskNum){//100个Runnable
        // 初始化线程池
        // 初始化runnable池
        runnablePool = new ArrayList<>(threadNum);
        // executorService添加runnable实例，并直接运行
        for (int i = 0; i < threadNum; i++) {
            ScheduledExecutorService thread = Executors.newSingleThreadScheduledExecutor();
            MyTaskRunnable runnable= new MyTaskRunnable(taskNum);
            runnablePool.add(runnable);// 需要添加池中进行task任务操作
            thread.scheduleAtFixedRate(runnable,1,3,TimeUnit.SECONDS);
        }
    }

    // 执行任务
    public void addTask(MyTask task) throws Exception {
        //随机添加到某个runnable-- 轮询添加
        runnablePool.get(sineNo).add(task);// 向任务池中轮询添加
        reSign();
    }
    private void reSign(){
        sineNo++;
        if (sineNo==runnablePool.size()){// 循环 -- 如果达到最大，重新循环
            sineNo = sineNo-runnablePool.size();
        }
    }
}
