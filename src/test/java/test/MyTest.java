package test;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import thread.MyTask;
import thread.MyTaskRunnable;
import thread.MyTaskRunnableThreadPool;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    public void testS(){
        String a = "a1";
        String b = "a" + 1;
        System.out.println((a ==b)); //result = true

        String c = "a1";
        System.out.println(a==c);// true
    }
    @Test
    public void testStr(){
        String s = "###";
        System.out.println(s.lastIndexOf("##"));
    }

    @Test
    public void testTime(){
        String s = "2015-08-16 08:12:00";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d h:m");
        System.out.println(simpleDateFormat.format(date));

    }
    @Test
    public void hashMapTest(){// hashMap无序：指的是 存放顺序与取出顺序不一致；实际hashMap是按照hashCode进行某种顺序的存放，一般计算后等同于字母表顺序
        Map<String ,String> map = new HashMap<>();
        map.put("f","a");
        map.put("c","a");
        map.put("d","a");
        map.put("g","a");
        map.put("e","a");
        map.put("a","a");
        for (int i = 0; i < 10; i++) {
            System.out.println(map.keySet());
        }
    }
    @Test
    public void testList(){
        List<String> list = new ArrayList<>();
        list.add("s");
        list.add("t");
        list.add("r");
        //List<String> list1 = list.subList(0,3);

        //list = list.subList(0,3);
        list.add("i");
        //System.out.println(list1);
        System.out.println(list);

    }
    @Test
    public void testArrays(){
        int[] arr = {1,2,45,3};
        Arrays.sort(arr);
        System.out.println(arr);
    }
    @Test
    public void testString1() throws InterruptedException {
        List<JSONObject> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject4 = new JSONObject();
        jsonObject.put("ori","19800506");
        jsonObject1.put("ori","29800506");
        jsonObject2.put("ori","19800507");
        jsonObject4.put("ori","298005");
        list.add(jsonObject);
        list.add(jsonObject1);
        list.add(jsonObject2);
        list.add(jsonObject4);
        List<JSONObject> list1 = new ArrayList<>();
        list1.add(jsonObject);
        jsonObject.put("ori","fdsfdsfds");
        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                return o2.getString("ori").compareTo(o1.getString("ori"));
            }
        });
        for (JSONObject j :
                list) {
            System.out.println(j.getString("ori"));
        }

        System.out.println("=====");
        for (JSONObject j :
                list1) {
            System.out.println(j.getString("ori"));
        }
    }

    @Test
    public void  testHashMap(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("a","0");
        map.put("c","0");
        map.put("b","0");
        map.put("a","1");
        for (String s :
                map.keySet()) {
            System.out.println(s);
        }
    }
}
