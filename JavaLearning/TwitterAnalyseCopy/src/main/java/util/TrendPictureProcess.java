package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by makisucruse on 2017/6/5.
 */
public class TrendPictureProcess {
    private static final String rootPath = "/Users/makisucruse/Downloads/userInfo/trend/";

    public static void main(String[] args) throws IOException {
        process();
    }

    public static void process() {
        try {
            File file = new File(rootPath);
            File[] files = file.listFiles();
            Arrays.asList(files).parallelStream().forEach(f -> removeUnnecessaryPicture(f.getAbsolutePath()));
        } catch (IllegalArgumentException e) {
        }
    }

    private static void removeUnnecessaryPicture(String filePath) {
        File file = new File(filePath);
        File[] pictures = file.listFiles();
        if (pictures == null || pictures.length == 0) return;
        String maxLength = getMaxPictureLength(filePath);
        String maxSize = getMaxPictureSize(filePath);
        for (File picture : pictures) {
            if (!picture.getAbsolutePath().equals(maxLength) && !picture.getAbsolutePath().equals(maxSize)) {
                deleteFile(picture.getAbsolutePath());
            }
        }
        String sourcePath = "";
        String targetPath = "";
        for (File picture : pictures) {
            if (picture.getAbsolutePath().equals(maxLength)) {
                picture.renameTo(new File(picture.getParent() + "/t0.png"));
                if (maxLength.equals(maxSize)) {
                    sourcePath = picture.getParent() + "/t0.png";
                    targetPath = picture.getParent() + "/t1.png";
                }
            } else if (picture.getAbsolutePath().equals(maxSize))
                picture.renameTo(new File(picture.getParent() + "/t1.png"));
        }
        if (!"".equals(sourcePath)) {
            try {
                copyPicture(sourcePath, targetPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }

    private static String getMaxPictureLength(String filePath) {
        File picture = new File(filePath);
        File[] files = picture.listFiles();
        long max = -1;
        String fileName = "";
        if (files == null || files.length == 0) return "";
        for (File f : files) {
            if (f.length() > max) {
                max = f.length();
                fileName = f.getAbsolutePath();
            }
        }
        return fileName;
    }

    private static String getMaxPictureSize(String filePath) {
        File file = new File(filePath);
        int max = -1;
        String fileName = "";
        File[] pictures = file.listFiles();
        if (pictures == null || pictures.length == 0) return "";
        for (File picture : pictures) {
            BufferedImage sourceImg = null;
            try {
                sourceImg = ImageIO.read(new FileInputStream(picture));
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
            if (sourceImg == null) continue;
            int tmp = sourceImg.getWidth() * sourceImg.getHeight();
            if (max < tmp) {
                max = tmp;
                fileName = picture.getAbsolutePath();
            }
        }
        return fileName;
    }

    private static void copyPicture(String sourcePath, String targetPath) throws IOException {
        FileInputStream fis = new FileInputStream(sourcePath);
        FileOutputStream fos = new FileOutputStream(targetPath);
        byte[] buf = new byte[1024];
        int by = 0;
        while ((by = fis.read(buf)) != -1) {
            fos.write(buf, 0, by);
        }
        fos.close();
        fos.close();
    }
}
