package Proxy.Proxy2;

import Proxy.User;


/**
 * 动态代理：
 *
 * 1.目标类实现接口
 * 2.实现IncocationHandler中的invok方法
 * 3.创建一个目标类对象
 * 4.调用工具类Proxy.newProxyInstance(classloader,interfer,invocationhandler)
 *   目标对象的类加载器、目标对象的接口数组、invocationhandler
 *   在这个方法中创建了一个代理类加载到内存中，.class,二进制字节码文件
 *   这个类实现了相同的接口，并且继承了Proxy类
 *   这也是为什么动态代理不能实现继承的原因，因为java不支持多继承
 *
 *
 * 5.当生成的代理对象调用方法时候，首先调用了invoke方法
 *    因为invocationhandler是代理对象类的成员变量
 *
 *
 *
 *
 *
 *
 *
 */
public class Proxy2 {

    public static void main(String[] args){
       User userObj = new UserImpObj();
       ProxyFactory  factory = new ProxyFactory(userObj);
       User userProxy = factory.getProxyObj();
       userProxy.doSomething();
    }

}
