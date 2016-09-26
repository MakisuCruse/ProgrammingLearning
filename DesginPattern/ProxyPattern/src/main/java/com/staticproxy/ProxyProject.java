package com.staticproxy;

/**
 * Created by makisucruse on 16/9/12.
 */
public class ProxyProject implements AbstractObject {
    AbstractObject object = new RealObject();

    @Override
    public void operation() {
        //do something..
        System.out.println("before calling...");
        object.operation();
        System.out.println("after calling...");
    }

}
