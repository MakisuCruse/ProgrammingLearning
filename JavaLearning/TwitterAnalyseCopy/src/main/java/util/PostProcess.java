package util;

/**
 * Created by makisucruse on 2017/5/17.
 */
public class PostProcess {
    public static String eliminate(String text) {
        StringBuilder builder = new StringBuilder();
        String[] strs = text.split("\n");
        for (String str : strs) {
            if (str.trim().equals("")) continue;
            String[] tmp = str.split(" ");
            for (String s : tmp) {
                //删除尾部的连接
                if (s.startsWith("http")) continue;
                //删除显示为乱码的字符
                if (s.matches("[\\w]*[$\\w]+[\\w]*")) {
                    String[] ts = s.split("[^\\w\\pP]+");
                    for (String s1 : ts) {
                        builder.append(s1).append(" ");
                    }
                } else {
                    builder.append(s).append(" ");
                }
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String text = "RT @leeconcuera: Good News: American Idol is back\n" +
                "\n" +
                "Bad News: Kelly Clarkson will be the new judge for The Voice.\uD83E\uDD23\uD83E\uDD23\uD83E\uDD23";
        System.out.println(eliminate(text));
    }
}
