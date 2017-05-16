package util;


/**
 * Created by makisucruse on 2017/3/30.
 */
public class CompleteSplit {
    public static void main(String[] args) {
        String str = "I will leave you with this cute Onew and Taemin moment à¸ªà #ShineeWorldInLA:  https://t.co/GF6SfllVIe ";
        System.out.println(splitTweet(str));
    }

    public static String splitTweet(String text) {
        //1. 完全拆分,所有符号," "
        String[] s1 = text.split(" ");
        //2. 先过滤掉不需要的东西比如奇怪的符号或者url
        StringBuilder builder = new StringBuilder();
        for (String s : s1) {
            if (s.matches("^http.*")) continue;
            builder.append(s).append(" ");
        }
        String[] s2 = builder.toString().split("[^a-z|A-Z|0-9|@|#]");
        builder = new StringBuilder();
        for (String s : s2) {
            if ("#".equals(s) || "@".equals(s) || "".equals(s)) continue;
            builder.append(s).append(" ");
        }
        return builder.toString();
    }
}
