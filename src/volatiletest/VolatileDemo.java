package volatiletest;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData
{
    // int  number = 0;
    volatile int number = 0;
    AtomicInteger numberAtomic = new AtomicInteger();


    public void addT060(){
        this.number = 60;
    }

    public void addPlusPlus(){
        number++;
    }

    public void addAtomicPlus(){
        numberAtomic.incrementAndGet();
    }
}
/**
 * 1.验证volatile 的原子性
 *    1.1 t1线程加public void addT060()，对主线程不可见，除非加上volatile
 *    1.2 加上volatile
 * 2.验证volatile的不保证原子性
 *    1.why number++ 3步   ，主内存读取 到 工作内存  ， 修改值  ，写入主内存
 * 3.解决不保证原子性
 *     synchronized
 *
 */

public class VolatileDemo {
    public static void main(String args[]){
        MyData myData = new MyData();
        for(int i=1; i<=20;i++){
            new Thread(()->{
                for (int n=1;n<=1000;n++){
                    myData.addPlusPlus();
                    myData.addAtomicPlus();
                }
            },"thread"+i).start();
        }
        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t number:"+ myData.number);
        System.out.println(Thread.currentThread().getName()+"\t number atomic:"+ myData.numberAtomic);
    }


    // volatile 可见性
    public static void volatileView(){
        MyData myData = new MyData();
        /**
         * ctrl + alt + t   快捷try catch
         */
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(5);
                myData.addT060();
                System.out.println(Thread.currentThread().getName()+"\t update number value :"+ myData.number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
            myData.addT060();
        },"t1").start();

        while(myData.number==0){
        }
        System.out.println(Thread.currentThread().getName()+"\t ,miss is over");
    }
}
