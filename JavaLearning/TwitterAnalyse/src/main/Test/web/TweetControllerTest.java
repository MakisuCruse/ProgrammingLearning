package web;

import org.junit.Test;

import java.util.*;

/**
 * Created by makisucruse on 2017/5/11.
 */
public class TweetControllerTest {
    @Test
    public void test() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 10; i >= 0; i--) {
            map.put("" + i, i);
        }
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            System.out.println(m.getKey() + ";" + m.getValue());
        }
    }
}
