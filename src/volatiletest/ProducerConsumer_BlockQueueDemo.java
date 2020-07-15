package volatiletest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *   要求： 一次生产，一次消费 ，执行若干次，被叫停
 *
 *
 *
 */
class MyResource{
    private volatile Boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null ;

    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    public void Producer() throws Exception {
        String data = null;
        Boolean retValue;
        while (flag) {
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName()+"\t生产成功"+data);
            }else {
                System.out.println(Thread.currentThread().getName()+"\t生产失败"+data);
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t被叫停");
    }


    public void Consumer() throws Exception {
        String data = null;
        while (flag) {
            data = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (data==null || data=="") {
                flag = false;
                System.out.println(Thread.currentThread().getName()+"\t消费失败"+data);
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消费成功"+data);
//            TimeUnit.SECONDS.sleep(2);
        }
        System.out.println(Thread.currentThread().getName()+"\t被叫停");
    }

    public void setFlag(Boolean flag){
        this.flag = flag;
    }

}

/**
 * volatile/CAS/AtomicInteger/BlockQueue/线程交互/原子引用
 */
public class ProducerConsumer_BlockQueueDemo {
    public static void main(String args[]){
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(3) );
        new Thread(()->{
            System.out.println("生产者线程进入");
            try {
                myResource.Producer();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        },"AAA_Pro").start();


        new Thread(()->{
            System.out.println("消费者线程进入");
            try {
                myResource.Consumer();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        },"BBB_Con").start();



        System.out.println();
        System.out.println();
        System.out.println();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }


        myResource.setFlag(false);
        System.out.println("主线程叫停");


    }
}
