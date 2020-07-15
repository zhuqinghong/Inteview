package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO     同步阻塞IO的多线程写法
 *
 * 同步：    A调用B，B执行返回结果给A，A继续执行
 * 非同步：  A调用B，B直接返回一个-1或者null， A继续执行
 *
 *
 * 阻塞：    A调用B，如果B没有返回给我，我就挂起线程，停止在这里不会继续执行
 * 非阻塞：  A调用B，B直接就有个返回结果，我继续执行，当B返回
 *
 *
 * socket  => fd 文件描述符 3
 * bind  （3，8090）
 * listen 3
 * while（true）
 * accept （3， =5 阻塞
 *
 *
 * 优势：1.接受很多客户端连接
 * 缺点：1.线程多浪费内存
 *       2.cpu调度消耗时间片
 *
 * 根源：accept， recv
 */
public class Server_BIO {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8090);
        System.out.println("step1: new ServerSocket(8090)");

        while(true){

            // 该处阻塞在accept()等待链接
            Socket client = serverSocket.accept();
            System.out.println("step2: client \t"+client.getPort());

            new Thread(()->{
                try {
                    InputStream inputStream = client.getInputStream();
                    byte[] bytes = new byte[1024];
                    // BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream));
                    while(inputStream.read(bytes)!=-1){
                        // read是阻塞的
                        System.out.println(new String(bytes));
                    }
                } catch (IOException e) {
                }
            }).start();
        }
    }
}
