import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by makisucruse on 2017/6/5.
 */
public class PictureSize {
    private static String filePath;
    private static String rootPath;
    private static String sourcePath;
    private static String targetPath;

    @BeforeClass
    public static void beforeClass() {
        filePath = "/Users/makisucruse/Downloads/testPhoto/#ExtremeRules";
        rootPath = "/Users/makisucruse/Downloads/userInfo/trend/";
        sourcePath = "/Users/makisucruse/Downloads/testPhoto/#ExtremeRules/t0.png";
        targetPath = "/Users/makisucruse/Downloads/testPhoto/#ExtremeRules/t1.png";
    }

    @Test
    public void testCopyPic() throws IOException {
        File file = new File(rootPath);
        File[] files = file.listFiles();
        boolean containT0 = false;
        for (File f : files) {
            File[] subFile = f.listFiles();
            if (subFile == null || subFile.length == 0) continue;
            if (subFile.length == 1) {
                for (File file1 : subFile) {
                    if (file1.getName().equals("t0.png")) containT0 = true;
                }
                if (containT0) {
                    copyPic(subFile[0].getAbsolutePath(), subFile[0].getParent() + "/t1.png");
                } else {
                    copyPic(subFile[0].getAbsolutePath(), subFile[0].getParent() + "/t0.png");
                }

            }
        }
    }

    //测试是否含有非两个trendPhoto的对象
    @Test
    public void testNot2Photo() {
        File file = new File(rootPath);
        File[] files = file.listFiles();
        for (File f : files) {
            File[] subFile = f.listFiles();
            if (subFile != null && subFile.length != 2) System.out.println(f.getName());
        }
    }

    private static void copyPic(String sourcePath, String targetPath) throws IOException {
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

    @Test
    public void testRemoveUnnecessary() {
        removeUnnecessaryPicture(filePath);
    }

    @Test
    public void testNewFile() {
        File file = new File("/Users/makisucruse/Downloads/testPhoto/#ExtremeRules/0.png");
        String oldPath = file.getAbsolutePath();
        String newPath = file.getParent() + "/1.png";
        Copy(oldPath, newPath);
    }

    public static void Copy(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }

    @Test
    public void testRename() {
        File file = new File(filePath);
        File[] files = file.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.getName().equals("1.png")) {
                f.renameTo(new File(f.getParent() + "0.pmg"));
            }
        }
    }

    @Test
    public void testFile() {
        File file = new File(rootPath);
        File[] files = file.listFiles();
        for (File f : files) System.out.println(f.getAbsoluteFile());
    }

    @Test
    public void testRootPath() {
        File file = new File(rootPath);
        File[] files = file.listFiles();
        for (File f : files) {
            removeUnnecessaryPicture(f.getAbsolutePath());
        }
    }

    @Test
    public void testGetPicture() {
        String ret = getMaxPictureSize(filePath);
        System.out.println(ret);
    }

    @Test
    public void testGetMaxPictureLength() {
        System.out.println(getMaxPictureLength(filePath));
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
        for (File picture : pictures) {
            if (picture.getAbsolutePath().equals(maxLength))
                picture.renameTo(new File(picture.getParent() + "/t0.png"));
            if (picture.getAbsolutePath().equals(maxSize))
                picture.renameTo(new File(picture.getParent() + "/t1.png"));
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
            } catch (IOException e) {
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


}
