package Proxy.Proxy1;

import Proxy.User;
/**
 * 目标对象类实现 UserService接口
 */
public class UserImpObj implements User {
    @Override
    public void doSomething() {
        System.out.println("这是一个目标对象执行----------doSomething");
    }
}

