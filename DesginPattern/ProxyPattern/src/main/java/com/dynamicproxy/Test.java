package com.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by makisucruse on 16/9/12.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Car car = new Car();
        InvocationHandler handler = new TimeHandler(car);
        Class<?> cl = car.getClass();
        Moveable m= (Moveable) Proxy.newProxyInstance(cl.getClassLoader(), cl.getInterfaces(), handler);
        m.move();
    }
}
