package core.service;

import core.DAO.Impl.TweetDaoImpl;
import core.model.Tweet;
import util.AccessDAO;
import util.WriteFile;

import java.util.List;

/**
 * Created by makisucruse on 2017/3/29.
 */
public class DataToFile {
    public static void main(String[] args) {
        String trendName = "";
        dataToFile(trendName);
    }

    private static void dataToFile(String trendName) {
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        List<Tweet> list = dao.findTrendTweet(trendName);
        StringBuilder builder = new StringBuilder();
        for (Tweet t : list) {
            builder.append(t.getText()).append("\r");
        }
        String fileName = "/Users/makisucruse/Downloads/OutPutFile/" + trendName + ".txt";
        WriteFile.writeMethod(fileName, builder.toString());
    }
}
