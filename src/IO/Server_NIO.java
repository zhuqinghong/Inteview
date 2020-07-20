package IO;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 *NIO 非阻塞IO  调用方法一定有返回，或者NULL或者有连接client
 *
 * 优点：1.不需要很多线程连接
 * 缺点：1.当有1w个客户端连接，但是只有一个输入IO，这样我要循环1w次去系统调用recv，循环内核空间切换用户空间1w次
 *
 *
 */
public class Server_NIO {
    public static void main(String[] args) throws Exception {
        LinkedList<SocketChannel> clients = new LinkedList<>();

        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(9090));
        ss.configureBlocking(true);
        //设置非阻塞


        while(true){
            // 接受客户端的连接
            Thread.sleep(1000);
            SocketChannel client = ss.accept();
            // accept 调用内核了 ： 1.没有客户端连接就返回NULL或者-1，
            //                      2.BIO会停止在这里，无返回，NIO是返回null，继续执行下去
            //                      3.有客户端连接就返回fd 5 client

            if (client == null){
                System.out.println("null................");
            }else{
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client.....port: " + port);
                clients.add(client);
            }

            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
            // 遍历已经连接进来的客户能不能读写数据

            for (SocketChannel c:clients){
                int num = c.read(buffer);

                // 这个read 发送recv的系统调用
                if (num>0){
                    buffer.flip();
                    byte[] aaa = new byte[buffer.limit()];
                    buffer.get(aaa);
                    String b = new String(aaa);
                    System.out.println(c.socket().getPort()+":" + b);
                    buffer.clear();
                }
            }
        }
    }
}
