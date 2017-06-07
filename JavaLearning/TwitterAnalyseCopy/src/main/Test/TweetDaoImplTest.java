import core.DAO.Impl.TopicDaoImpl;
import core.DAO.Impl.TweetDaoImpl;
import core.model.Topic;
import core.model.Tweet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class TweetDaoImplTest {
    @Autowired
    private TweetDaoImpl tweetDao;

    @Autowired
    private TopicDaoImpl topicDao;

    @Autowired
    private JdbcTemplate template;

    @Test
    public void tweetDaoShouldNotNull() {
        Assert.assertNotNull(template);
    }

    @Test
    public void tweetPerHourCount() {
        String trendName = "#NationalTeachersDay";
        List<Integer> lst = tweetDao.findPerHourTweetCount(trendName);
        lst.forEach(System.out::println);
        System.out.println(lst.size());
    }

    @Test
    public void testFindTweet() {
        String tweetId = "861952267720765440";
        Tweet tweet = tweetDao.findTweet(tweetId);
        System.out.println(tweet);
    }

    @Test
    public void findTodayTrend() {
        List<String> trends = tweetDao.findTodayTrends();
        System.out.println(trends.size());
    }

    @Test
    public void testFindOneTopic() {
        String topic = "#HarryStylesTODAY";
        Topic ret = topicDao.findTopic(topic);
        System.out.println(ret);
    }

    @Test
    public void testNearThreeDayTrends() {
        System.out.println(tweetDao.findNearTwoDayTrends().size());
        ;
    }
}
