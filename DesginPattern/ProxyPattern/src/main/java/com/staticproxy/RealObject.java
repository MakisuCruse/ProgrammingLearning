package com.staticproxy;

import java.util.Date;

/**
 * Created by makisucruse on 16/9/12.
 */
public class RealObject implements AbstractObject{

    @Override
    public void operation() {
        System.out.println(new Date());
    }
}
