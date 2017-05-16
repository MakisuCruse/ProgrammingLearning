package util;


import java.io.*;
import java.util.ArrayList;

/**
 * Created by makisucruse on 2017/3/27.
 */
public class ReadFile {
    public static void main(String[] args) {

//        String path = ReadFile.class.getClassLoader().getResource("stopword").getPath();
        ArrayList<String> ret = readTxt("stopword");
        for (String s : ret) {
            System.out.println(s);
        }

    }


    public static ArrayList<String> readTxt(String fileName) {
        String filePath = ReadFile.class.getClassLoader().getResource(fileName).getPath();
        ArrayList<String> ret = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                ret.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
}
