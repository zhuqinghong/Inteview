package Proxy.Proxy1;


/**
 * 静态代理
 *
 * 1. 目标类实现接口
 * 2. 代理类实现接口
 * 3. 创建代理对象，注入一个目标类对象
 * 4. 调用代理类的对象，方法A，A中调用目标类的对象方法
 *
 */


public class Proxy1 {


    public static void main(String[] args){

        UserImpObj userObj= new UserImpObj();

        UserImpProxy userServiceImpProxy = new UserImpProxy(userObj);

        userServiceImpProxy.doSomething();

    }
}
