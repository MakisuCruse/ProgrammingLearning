package core.service;

import core.Algo.FrequentCount;
import core.DAO.Impl.TweetDaoImpl;
import core.model.Tweet;
import util.AccessDAO;
import util.CompleteSplit;
import util.ConvertForR;
import util.WriteFile;

import java.util.*;

/**
 * Created by makisucruse on 2017/3/23.
 */
public class TweetAnalyse {

    public static void main(String[] args) {
        TweetAnalyse tweetAnalyse = new TweetAnalyse();
//        String ret = tweetAnalyse.analyseTweet("#TrumpTeachesHistory");
        tweetAnalyse.analyseTweet();
//        System.out.println(ret);
    }

    private String analyseTweet(String trend) {
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        List<Tweet> tweets = dao.findTrendTweet(trend);
        StringBuilder builder = new StringBuilder();
        for (Tweet tweet : tweets) {
            builder.append(CompleteSplit.splitTweet(tweet.getText())).append(" ");
        }
        String[] str = builder.toString().split(" ");
        List<Map.Entry<String, Integer>> ret = FrequentCount.analyseTrend(str, trend);
        StringBuilder b = new StringBuilder();
        b.append("{");
        for (Map.Entry<String, Integer> m : ret) {
            b.append("[").append(m.getKey()).append(",").append(m.getValue()).append("]");
        }
        b.append("}");
        return b.toString();
    }

    private void analyseTweet() {
        Map<String, List<Map.Entry<String, Integer>>> wordCount = new HashMap<>();
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        List<String> trends = dao.findTrends();
        for (String t : trends) {
            List<Tweet> tweets = dao.findTrendTweet(t);
            StringBuilder builder = new StringBuilder();
            for (Tweet tweet : tweets) {
                builder.append(CompleteSplit.splitTweet(tweet.getText())).append(" ");
            }
            String[] str = builder.toString().split(" ");
            List<Map.Entry<String, Integer>> entryList = FrequentCount.analyseTrend(str, t);
            //写文件
            String fileName = "/Users/makisucruse/Downloads/OutPutFile/" + t + ".txt";
            WriteFile.writeMethod(fileName, ConvertForR.convert(entryList));
            wordCount.put(t, entryList);
        }
        show(wordCount);
    }

    private static void show(Map<String, List<Map.Entry<String, Integer>>> list) {
        for (Map.Entry<String, List<Map.Entry<String, Integer>>> m : list.entrySet()) {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for (int i = 0; i < Math.min(m.getValue().size(), 20); i++) {
                builder.append("[").append(m.getValue().get(i).getKey()).append(",").append(m.getValue().get(i).getValue()).append("]");
            }
            builder.append("}");
            System.out.println("Topic:" + m.getKey() + ";WordCount:" + builder.toString());
        }
    }

}
