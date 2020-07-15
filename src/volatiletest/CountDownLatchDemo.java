package volatiletest;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 * 所有任务做完才能执行下一步任务
 *
 */
// before
//public class CountDownLatch {
//    public static void main(String args[]) throws Exception{
//        for (int i=1;i<=6;i++){
//            new Thread(()->{
//                System.out.println(Thread.currentThread().getName()+"\t离开");
//            },String.valueOf(i)).start();
//        }
//        System.out.println(Thread.currentThread().getName()+"\t班长走了");
//
//    }
//}
    enum Country {

    one(1, "齐国"), two(1, "楚国");

    private int countryCode;
    private String counttyName;

    public int getCountryCode() {
        return countryCode;
    }

    public String getCounttyName() {
        return counttyName;
    }

    Country(int countryCode, String counttyName) {
        this.countryCode = countryCode;
        this.counttyName = counttyName;
    }

    public static String getName(int i) {
        Country array[] = Country.values();
        for (Country country : array) {
            if (country.getCountryCode() == i) {
                return country.getCounttyName();
            }
        }
        return null;
    }
}



public class CountDownLatchDemo {
    public static void main(String args[]) throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i=1;i<=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t离开");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t班长走了");

    }
}
