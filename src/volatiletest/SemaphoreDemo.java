package volatiletest;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/***
 * semaphore 只有3个资源，6个线程，用一个释放一个资源
 *
 */
public class SemaphoreDemo {

    public static void main(String args[]){
        Semaphore semaphore =new Semaphore(3);

        for (int i=0;i<=7;i++){
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println("Thread"+Thread.currentThread().getName()+"\tcome in");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("come out");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            },String.valueOf(i)).start();
        }
    }
}
