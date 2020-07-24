package Proxy.Proxy1;

import Proxy.User;

/**
 *代理对象类实现 Userservice接口
 */
public class UserImpProxy implements User{
    private User user;
    UserImpProxy(User user){
        this.user = user;
    }

    @Override
    public void doSomething() {
        System.out.println("代理对象对目标对象进行代理------before doSomething");

        user.doSomething();

        System.out.println("代理对象对目标对象进行代理------after doSomething");
    }
}
