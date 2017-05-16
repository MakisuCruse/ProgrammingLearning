package util;

import java.util.List;
import java.util.Map;

/**
 * Created by makisucruse on 2017/5/11.
 */
public class ConvertForR {
    public static String convert(List<Map.Entry<String, Integer>> list) {
        int count = 1;
        StringBuilder builder = new StringBuilder();
        builder.append("trend~count \r");
        for (Map.Entry<String, Integer> m : list) {
            if (m.getValue() < 10) continue;
            if (m.getKey().matches("[ ]*")) continue;
            String key = m.getKey().replaceAll("\r", "");
            key = key.replaceAll("\n", "");
            builder.append(count++).append("~").append(key).append("~").append(m.getValue()).append("\r");
        }
        return builder.toString();
    }
}
