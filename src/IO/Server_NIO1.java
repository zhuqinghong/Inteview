package IO;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  IO多路复用
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
 *  创建fd1
 *
 * epoll_ctl
 *  将fd1假如内核内存空间，监听一个accept
 *
 *
 * epoll_wait
 *   获取IO状态
 *
 */
public class Server_NIO1 {

    public static  void main(String[] args){
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        Lock lock = new ReentrantLock();


    }
}


