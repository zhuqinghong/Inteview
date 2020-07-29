package IO;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Reactor
 * NIO 多路复用的线程池模式
 *
 * 思考：当select读或者写一个IO操作阻塞的时候，有新的客户端连接怎么办？
 *
 *
 * select ：  1.客户端连接
 *            2.IO 读写
 *
 *  我们可以将IO的读写交给一个线程池来完成
 *
 *
 *
 */
public class Server_NIO2 {


    public static  void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9090));
        ssc.configureBlocking(false);
        Selector selector = Selector.open();

        ssc.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务器启动成功------");

        while(true){
            // 阻塞方法，轮询，调用内核，获取状态
            selector.select();

            Set<SelectionKey> selectionKeys  = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while(iterator.hasNext()){
                SelectionKey key  = iterator.next();
                iterator.remove();

                if(key.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel client = serverSocketChannel.accept();

                    client.configureBlocking(false);
                    client.register(selector,SelectionKey.OP_READ);
                    System.out.println("客户端连接来了"+ client.getRemoteAddress());

                }else if(key.isReadable()){
                    SelectionKey key1 = key;

                    executorService.execute(()->{
                        SocketChannel sc = null;
                        try {
                            sc = (SocketChannel) key1.channel();
                            ByteBuffer buffer  = ByteBuffer.allocateDirect(512);
                            buffer.clear();

                            int len = sc.read(buffer);

                            // 这个很重要哦
                            buffer.flip();
                            if (len!=-1){
                                byte[] aaa = new byte[buffer.limit()];
                                buffer.get(aaa);
                                String b = new String(aaa);
                                //System.out.println(b);
                                System.out.println(Thread.currentThread()+"线程 处理————————————————————-"+b);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
//                            if(sc!=null){
//                                try {
//                                    sc.close();
//                                }catch (IOException e){
//                                    e.printStackTrace();
//                                }
//                            }
                        }
                    });
                }

            }
        }

    }
}
