package core.service;

import core.DAO.Impl.TopicDaoImpl;
import core.DAO.Impl.TweetDaoImpl;
import core.model.Topic;
import util.AccessDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by makisucruse on 2017/6/2.
 */
public class TweetService {
    private static TweetDaoImpl tweetDao = (TweetDaoImpl) AccessDAO.getTweetDao();
    private static TopicDaoImpl topicDao = (TopicDaoImpl) AccessDAO.getTopicDao();

    public static List<Topic> findTopics() {
        return topicDao.findTopics();
    }

    public static Topic findTopic(String trendName) {
        return topicDao.findTopic(trendName);
    }

    public static List<String> findTodayTopics() {
        List<String> lst = tweetDao.findTodayTrends();
        List<String> ret = new ArrayList<>();
        if (lst.size() > 5) {
            Random random = new Random();
            while (ret.size() < 5) {
                int randNum = random.nextInt(lst.size() - 1);
                if (!ret.contains(lst.get(randNum))) ret.add(lst.get(randNum));
            }
            return ret;
        }
        return lst;
    }

    public static List<String> findNearTwoDayTopics() {
        return tweetDao.findNearTwoDayTrends();
    }
}
