import org.junit.Test;

import java.io.File;

/**
 * Created by makisucruse on 2017/5/24.
 */
public class RenameFile {
    private static void renameFile() {
        String fileLocation = "/Users/makisucruse/Downloads/trendPhoto";
        File file = new File(fileLocation);
        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println(f.getName());
        }
        File oldFile = new File(fileLocation);
        File newFile = new File(fileLocation);
        oldFile.renameTo(newFile);
    }

    @Test
    public void test() {
        renameFile();
    }

    @Test
    public void getRightNowMaxFileName() {
        String fileLocation = "/Users/makisucruse/Downloads/trendPhoto";
        File file = new File(fileLocation);
        File[] files = file.listFiles();
        for (File f : files) {
            String subFileLocation = f.getAbsolutePath();
            File subFile = new File(subFileLocation);
            File[] listFiles = subFile.listFiles();
            if (listFiles == null) continue;
            for (File file1 : listFiles) {
                System.out.println(file1.getAbsolutePath());
            }
        }
    }
}
