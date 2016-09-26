package com.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by makisucruse on 16/9/12.
 */
public class TimeHandler implements InvocationHandler {
    private Object target;

    public TimeHandler(Object object) {
        super();
        this.target = object;
    }

    /**
     * InvocationHandler事务管理器
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        method.invoke(target, args);
        long end = System.currentTimeMillis();
        System.out.println("time:" + (end - start));
        return null;
    }
}
