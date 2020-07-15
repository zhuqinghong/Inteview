package volatiletest;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 *
 */

class MyThread1 extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println("继承Thread创建 线程");
    }
}
class MyThread2 implements Runnable{
    @Override
    public void run() {
        System.out.println("实现Runnable接口");
    }
}

class MyThread3 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("实现Callable接口");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2020;
    }
}


public class CallableDemo {
    public static void main(String args[]) throws Exception{
        Thread thread1 = new MyThread1();
        thread1.start();

        Thread thread2 = new Thread(new MyThread2(),"t2");
        thread2.start();

        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread3());
        Thread thread3 = new Thread(futureTask,"t3");
        Thread thread4 = new Thread(futureTask,"t4");
        // 同一个task只有执行一次
        thread3.start();
        thread4.start();

        int i=1;
        System.out.println("result:"+(i+futureTask.get()));
    }



}
