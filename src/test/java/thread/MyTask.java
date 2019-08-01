package thread;

/**
 * 任务模块
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/12 15:39
 */
public abstract class MyTask{
    private boolean running =  true;
    public abstract void doThis();
    public void stop(){
        running = false;
    }
    public boolean running(){
        return running;
    }
}