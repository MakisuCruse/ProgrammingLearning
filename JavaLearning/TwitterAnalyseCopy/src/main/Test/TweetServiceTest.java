import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import core.Algo.SimpleFrequentCount;
import core.DAO.Impl.TopicDaoImpl;
import core.DAO.Impl.TweetDaoImpl;
import core.model.Topic;
import core.service.TweetService;
import org.junit.BeforeClass;
import org.junit.Test;
import util.AccessDAO;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by makisucruse on 2017/6/3.
 */
public class TweetServiceTest {
    private static TweetDaoImpl tweetDao;
    private static TopicDaoImpl topicDao;

    @BeforeClass
    public static void setUpBeforeClass() {
        tweetDao = (TweetDaoImpl) AccessDAO.getTweetDao();
        topicDao = (TopicDaoImpl) AccessDAO.getTopicDao();
    }


    @Test
    public void testTweetService() {
        TweetService.findTodayTopics().forEach(System.out::println);
    }

    @Test
    public void testRandom() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(50));
        }
    }

    @Test
    public void testAddTopic() {
        Topic topic = new Topic("test", "??", "not null", "not null", "123");
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        topicDao.addTopic(topic);
    }

    @Test
    public void testParallelStream() {
        long start = System.currentTimeMillis();
        List<String> lst = tweetDao.findTodayTrends().subList(35, 40);
        lst.forEach(System.out::println);
        lst.forEach(SimpleFrequentCount::topicAnalyse);
        long end = System.currentTimeMillis();
        System.out.println("共计用时:" + (end - start));
    }

    @Test
    public void testParallel() {
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<String> lst = tweetDao.findTodayTrends().subList(60, 65);
        lst.forEach(System.out::println);
        for (String s : lst) {
            service.submit((Runnable) () -> SimpleFrequentCount.topicAnalyse(s));
        }
        service.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("共计用时:" + (end - start));
    }

    @Test
    public void testFindInTweetNotInTopic() {
        System.out.println(tweetDao.findInTweetNotInTopic().size());
    }

    @Test
    public void testConvertToUTF8() throws UnsupportedEncodingException {
        String trendName = "#18for18";
//        for (Map.Entry<String, Integer> m : SimpleFrequentCount.getResultDependsOnScore(trendName)) {
//            System.out.println(m.getKey() + ";" + m.getValue());
//        }
        System.out.println("===");
        Topic topic = topicDao.findTopic(trendName);
        String description = topic.getDescription();
        JSONArray array = JSON.parseArray(description);
        array.forEach(System.out::println);

    }
}
