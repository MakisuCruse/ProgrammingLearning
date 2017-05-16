package util;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by makisucruse on 2017/3/29.
 */
public class WriteFile {

    public static void writeMethod(String fileName, String str) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
