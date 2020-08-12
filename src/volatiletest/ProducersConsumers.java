package volatiletest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Resource{
    private int i=0;
    private int MAX=10;
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition1 = reentrantLock.newCondition();
    private Condition condition2 = reentrantLock.newCondition();

    Resource(int MAX){
        this.MAX=MAX;
    }

    public void increasement() throws InterruptedException{
        reentrantLock.lock();
        try {
            while (i>=MAX){
                //System.out.println(Thread.currentThread().getName()+":生产线程等待..........");
                condition1.await();
            }
            i++;
            System.out.println(Thread.currentThread().getName()+":生产线程生产成功........."+i);
            condition1.signalAll();
            condition2.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //System.out.println(Thread.currentThread()+"生产线程释放lock");
           // System.out.println();
            reentrantLock.unlock();
        }
    }


    public void decreasement() throws InterruptedException{
        reentrantLock.lock();
        try {
            while (i<=0){
                //System.out.println(Thread.currentThread().getName()+":消费线程等待..........");
                condition2.await();
            }
            i--;
            System.out.println(Thread.currentThread().getName()+":消费线程消费成功........."+i);
            condition1.signalAll();
            condition2.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // System.out.println(Thread.currentThread()+"消费线程释放lock");
           // System.out.println();
            reentrantLock.unlock();
        }
    }
}

class Resource2{
    private int i=0;
    private int MAX=10;


    Resource2(int MAX){
        this.MAX=MAX;
    }

    public void increasement() throws InterruptedException{
        synchronized (this){
            try {
                while (i>=MAX){
                    //System.out.println(Thread.currentThread().getName()+":生产线程等待..........");
                    this.wait();
                }
                i++;
                System.out.println(Thread.currentThread().getName()+":生产线程生产成功........."+i);
                this.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //System.out.println(Thread.currentThread()+"生产线程释放lock");
                //System.out.println();
            }
        }

    }


    public void decreasement() throws InterruptedException{
       synchronized (this){
           try {
               while (i<=0){
                   //System.out.println(Thread.currentThread().getName()+":消费线程等待..........");
                   this.wait();
               }
               i--;
               System.out.println(Thread.currentThread().getName()+":消费线程消费成功........."+i);
               this.notifyAll();
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               //System.out.println(Thread.currentThread()+"消费线程释放lock");
               //System.out.println();

           }
       }
    }
}


public class ProducersConsumers {
    public static void main(String[] args) throws Exception{
        Resource resource = new Resource(5);

        Thread thread1 = new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resource.increasement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A");

        Thread thread2 = new Thread(()->{
            for (int i=0;i<10;i++){
                try {
                    resource.decreasement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B");

        Thread thread3 = new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resource.increasement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C");

        Thread thread4 = new Thread(()->{
            for (int i=0;i<10;i++){
                try {
                    resource.decreasement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }
}
