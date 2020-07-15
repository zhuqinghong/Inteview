package volatiletest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier
 * 到了先等待，到齐执行
 *
 *
 */
public class CycliBarrierDemo {
    public static void main(String args[]){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6,()->{
            System.out.println("大家到齐了我执行");
        });

        for (int i=1;i<=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t到了");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                }
            },String.valueOf(i)).start();
        }
    }
}
