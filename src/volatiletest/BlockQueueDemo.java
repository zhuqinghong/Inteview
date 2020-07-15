package volatiletest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueDemo {
    public static void main(String args[]){
        BlockingQueue<String>
                blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            blockingQueue.put("a");
            blockingQueue.put("b");
            blockingQueue.put("c");
            blockingQueue.put("d");

            blockingQueue.remove();
            blockingQueue.remove();
            blockingQueue.remove();
            blockingQueue.remove();

        } catch (Exception e) {
           System.out.println(e.toString());
        }

    }
}
