import core.service.ExtractTweetData;
import org.junit.Test;
import util.AccessDAO;

import java.io.File;
import java.util.List;

/**
 * Created by makisucruse on 2017/5/25.
 */
public class FileCount {
    public static void main(String[] args) {
        String fileLocation = "/Users/makisucruse/Downloads/TwitterAnalyseCopy/src/main/webapp/WEB-INF/trend";
        File file = new File(fileLocation);
        System.out.println(file.listFiles().length);
    }

    @Test
    public void shouldDownload() {
        core.DAO.Impl.TweetDaoImpl dao = (core.DAO.Impl.TweetDaoImpl) AccessDAO.getTweetDao();
        List<String> lists = dao.findTrends();
        String fileLocation = "/Users/makisucruse/Downloads/TwitterAnalyseCopy/src/main/webapp/WEB-INF/trend";
        File file = new File(fileLocation);
        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println(f.getName() + ";" + ExtractTweetData.shouldDownload(f.getName()));
        }
    }
}
