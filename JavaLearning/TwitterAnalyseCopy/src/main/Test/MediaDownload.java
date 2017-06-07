import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by makisucruse on 2017/5/24.
 */
public class MediaDownload {
    private static boolean shouldDownload(String trendName) {
        String fileLocation = "/Users/makisucruse/Downloads/trendPhoto/" + trendName + "/";
        File file = new File(fileLocation);
        if (!file.exists()) file.mkdir();
        return file.list().length <= 3;
    }

    @Test
    public void testShouldDownload() {
        System.out.println(shouldDownload("#trump"));
    }

    @Test
    public void testMapGet() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("#trump", true);
        if (map.get("#trump") != null && map.get("#trump")) System.out.println(map.get("#trump"));
    }
}
