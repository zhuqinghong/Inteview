package volatiletest;

import sun.nio.ch.ThreadPool;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolDemo {
    public static void main(String args[]){
        ExecutorService executorService1= Executors.newFixedThreadPool(5);
        ExecutorService executorService2=Executors.newSingleThreadExecutor();
        ExecutorService executorService3=Executors.newCachedThreadPool();
        ExecutorService executorService4=Executors.newScheduledThreadPool(3);

        try {
            executorService1.execute(()->{

                //

            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService1.shutdown();
        }
    }
}
