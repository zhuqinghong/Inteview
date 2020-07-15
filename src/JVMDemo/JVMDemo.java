package JVMDemo;

import java.util.concurrent.TimeUnit;

import static java.lang.Integer.MAX_VALUE;

/**
 * JVM 的参数类型
 * 1. 标配版本
 *      -version , -help  , -showversion
 *
 * 2.-X参数
 *
 *      -Xint
 *      -Xcomp
 *      -Xmix
 *
 *
 * 3.-XX参数
 *
 * 两种：1.布尔类型
 *       jps -l    查看java进程号
 *       jinfo -flag  PrintGCDetails 进程编号
 *                    UseSerialGC    是否使用串行垃圾回收器
 *
 * 2.kv键值对类型
 *
 *   MetaspaceSize 元空间默认大小
 *
 *
 *  * 1.  jinfo -flags  进程标号    查看默认的jvm参数
 *  *
 *  * 2. java -XX:+PrintFlagsInitial -version
 *
 *
 *
 *
 *
 * 常用参数：
 * Xms    -XX:InitialHeapSize    初始堆大小   def   1/64
 * Xmx    -XX:MaxHeapSize                1/4
 *
 *
 *
 */



public class JVMDemo {
    public static void main(String args[]) throws Exception {
        System.out.println("#*****************");
        Thread.sleep(MAX_VALUE);


        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().maxMemory();
    }
}
