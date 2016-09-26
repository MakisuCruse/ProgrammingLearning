package com.dynamicproxy;

import java.util.Random;

/**
 * Created by makisucruse on 16/9/12.
 */
public class Car implements Moveable {
    @Override
    public void move() throws Exception {
        Thread.sleep(new Random().nextInt(1000));
        System.out.println("move...");
    }
}
