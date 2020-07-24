package Proxy.Proxy2;

import Proxy.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    //维护一个目标对象
    private Object target;
    ProxyFactory(Object target){
        this.target=target;
    }

    public  User getProxyObj(){
        User userProxy = (User) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("动态代理对象 ------  before doSomething");
                Object object = method.invoke(target,args);
                System.out.println("动态代理对象 ------  after doSomething");

                return object;
            }
        });
        return userProxy;
    }
}


