package test;

import org.junit.Test;
import thread.MyTask;
import thread.MyTaskRunnable;
import thread.MyTaskRunnableThreadPool;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 随便测试
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/28 17:13
 */
public class MyTest {
    // 判断10000以内的质数


    // 判断线程
    @Test
    public void testThread() throws IOException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable1 = ()->{
            System.out.println("嘿嘿");
        };
        scheduledExecutorService.scheduleWithFixedDelay(runnable1,1,3,TimeUnit.SECONDS);
        System.in.read();
    }

    // 判断线程
    @Test
    public void testMyTaskRunnable() throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        MyTaskRunnable runnable= new MyTaskRunnable(2);
        MyTask myTask = new MyTask() {
            @Override
            public void doThis() {
                System.out.println("哈哈");
            }
        };
        runnable.add(myTask);


        scheduledExecutorService.scheduleWithFixedDelay(runnable,1,3,TimeUnit.SECONDS);

       MyTask myTask1 = new MyTask() {
            int i=0;
            @Override
            public void doThis() {
                System.out.println(i);
                i++;
                System.out.println(i);
                if (i==4){
                    this.stop();
                }
                System.out.println("嘿嘿");
            }
        };
        runnable.add(myTask1);
        System.in.read();
    }


    // 判断线程
    @Test
    public void testMyTaskRunnableThreadPool() throws Exception {
        MyTaskRunnableThreadPool myTaskRunnableThreadPool = MyTaskRunnableThreadPool.getInstance();
        MyTask myTask0 = new MyTask() {
            @Override
            public void doThis() {
                System.out.println("嘎嘎"+Thread.currentThread().getName());
            }
        };
        MyTask myTask1 = new MyTask() {
            int i=0;
            @Override
            public void doThis() {
                System.out.println(i);
                i++;
                if (i==3){
                    this.stop();
                }
                System.out.println("嘿嘿"+Thread.currentThread().getName());
            }
        };
        MyTask myTask2 = new MyTask() {
            @Override
            public void doThis() {
                System.out.println("哈哈"+Thread.currentThread().getName());
            }
        };
        myTaskRunnableThreadPool.addTask(myTask0);
        myTaskRunnableThreadPool.addTask(myTask1);
        myTaskRunnableThreadPool.addTask(myTask2);
        System.in.read();
    }
@Test
    public void testString(){
        String a = "hello2";
        final String b = "hello";// 直接放到常量池--直接预编译期，然后
        String d = "hello";
        String c = b+2;// b 因为是final的所以直接拿到的b其实就是“hello”，编译期直接被转化为hello2
        String e = d+2; // 因为不是final的，所以拿到的d只是声明，最终需要执行期进行 append操作
        System.out.println(a==c);// true
        System.out.println((a==e));// false
    }
    @Test
    public void testt(){
        String a = "a1";
        String b = "a" + 1;
        System.out.println((a ==b)); //result = true

        String c = "a1";
        System.out.println(a==c);// true
    }
}
