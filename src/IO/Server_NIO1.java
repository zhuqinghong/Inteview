package IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  NIO多路复用
 *
 *
 *  使用一次系统调用
 *  查询到有哪些IO状态
 *
 *  内核的
 *  同步IO多路复用器  select 、 poll 、epoll
 * Select   连接个数限制
 * POll     连接个数无限制
 * 1.每次重复传递多个fds到内核
 * 2. 内核选出有事件的fds
 *
 * 缺点：1.每次重复传递fds   解决方案：epoll 的内核开辟内存空间
 *       2.每次遍历遍历fds
 *
 *
 *
 * Epoll
 *
 * 使用一个select轮询多个文件描述符
 * select可以传入连接文件描述符、 读文件描述符、写文件描述符
 *
 * epoll_create
 *  开辟内核空间
 *
 * epoll_ctl
 *  创建fd1，加入内核空间，监听一个accept
 *
 *
 * epoll_wait
 *   获取IO状态
 *
 */


/**
 *
 *
 * Reactor模式
 *
 */
public class Server_NIO1 {
    public static  void main(String[] args) throws IOException {
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.configureBlocking(false);
        ss.bind(new InetSocketAddress("127.0.0.1",9090));

        Selector selector = Selector.open();

        // 注册监听事件
        ss.register(selector, SelectionKey.OP_ACCEPT);


        while (true){
            Set<SelectionKey> keys = selector.keys();

            System.out.println(keys.size()+ "    size");

             // 阻塞方法，有事件发生了
            while (selector.select(500)>0){
                Set<SelectionKey> selectionKeys  = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectionKeys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();

                    if(key.isAcceptable()){

                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel client =ssc.accept();

                        client.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.allocateDirect(8192);

                        client.register(selector,SelectionKey.OP_READ,buffer);

                        System.out.println("-------------------------------");

                        System.out.println("新客户端连接" + client.getRemoteAddress());

                        System.out.println("-------------------------------");


                    }else if(key.isReadable()){
                        SocketChannel sc = null;
                        try {
                            sc = (SocketChannel) key.channel();
                            ByteBuffer buffer  = ByteBuffer.allocateDirect(512);
                            buffer.clear();

                            int len = sc.read(buffer);

                            // 这个很重要哦
                            buffer.flip();
                            if (len!=-1){
                                byte[] aaa = new byte[buffer.limit()];
                                buffer.get(aaa);
                                String b = new String(aaa);
                                System.out.println(b);
                            }

                            ByteBuffer byteBufferTOWrite  = ByteBuffer.wrap("Welocome".getBytes());
                            sc.write(byteBufferTOWrite);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if(sc!=null){
                                try {
                                    sc.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}


