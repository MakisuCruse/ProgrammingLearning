package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by makisucruse on 2017/5/9.
 */
public class URLConnectionDownloader {
    public static void main(String[] args) throws Exception {
        download("http://pbs.twimg.com/profile_images/823619885028741120/S3hVsKP4.jpg", "/Users/makisucruse/Downloads/tweetPhoto/1.png");
    }

    /**
     * 下载文件到本地
     *
     * @param urlString 被下载的文件地址
     * @param filename  本地文件名
     * @throws Exception 各种异常
     */
    public static void download(String urlString, String filename) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    private static String imageUrlToByte(String imageURL) throws IOException {
        URL url = new URL(imageURL);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        //1kb
        byte[] bs = new byte[1024];
        int len;
        StringBuilder builder = new StringBuilder();
        while ((len = is.read(bs)) != -1) {
            for (int i = 0; i < len; i++) {
                builder.append(byte2bits(bs[i]));
            }
        }
        is.close();
        return builder.toString();
    }

    public static String byte2bits(byte b) {

        int z = b;
//        z |= 256;
        String str = Integer.toBinaryString(z);
        int len = str.length();
        return str.substring(len - 8, len);
    }

    public static byte bit2byte(String bString) {
        byte result = 0;
        for (int i = bString.length() - 1, j = 0; i >= 0; i--, j++) {
            result += (Byte.parseByte(bString.charAt(i) + "") * Math.pow(2, j));
        }
        return result;
    }
}
