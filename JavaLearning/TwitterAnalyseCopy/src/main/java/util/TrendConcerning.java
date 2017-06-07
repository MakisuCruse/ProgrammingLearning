package util;

import core.Algo.FrequentCount;
import core.DAO.Impl.TweetDaoImpl;
import core.model.Tweet;
import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by makisucruse on 2017/4/14.
 */
public class TrendConcerning {
    public static void main(String[] args) {
        String text = getFrequentWords("Happy Passover");
        Sentence sentence = new Sentence(text);
        for (int i = 0; i < sentence.words().size(); i++) {
            System.out.println(sentence.word(i) + "~" + sentence.posTag(i));
        }
    }

    public static String getFrequentWords(String trend) {
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        List<Tweet> tweets = dao.findTrendTweet(trend);
        String str = transformTweetListToString(tweets);
        List<Map.Entry<String, Integer>> ret = FrequentCount.analyseTrend(str.split(" "), trend);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ret.size() && i < 20; i++) {
            builder.append(ret.get(i).getKey()).append(" ");
        }
        return builder.toString();
    }

    private static String transformTweetListToString(List<Tweet> tweets) {
        StringBuilder builder = new StringBuilder();
        for (Tweet t : tweets) {
            builder.append(t.getText()).append(" ");
        }
        return builder.toString();
    }

    public static List<String> getHighFrequentVerb(String trend) {
        String text = getFrequentWords(trend);
        Sentence sentence = new Sentence(text);
        List<String> ret = new ArrayList<>();
        List<String> posTags = sentence.posTags();
        for (int i = 0; i < posTags.size(); i++) {
            if (posTags.get(i).matches("V.*")) {
                ret.add(sentence.word(i));
            }
        }
        return ret;
    }

    public static List<Tweet> getTrendTweets(String trend) {
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        return dao.findTrendTweet(trend);
    }
}
