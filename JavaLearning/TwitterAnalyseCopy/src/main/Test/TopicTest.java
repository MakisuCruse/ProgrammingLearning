import core.DAO.Impl.TopicDaoImpl;
import core.DAO.Impl.TweetDaoImpl;
import core.model.Topic;
import org.junit.BeforeClass;
import org.junit.Test;
import util.AccessDAO;

import java.util.List;

/**
 * Created by makisucruse on 2017/6/2.
 */
public class TopicTest {
    private static TopicDaoImpl topicDao;

    @BeforeClass
    public static void beforeClass() {
        topicDao = (TopicDaoImpl) AccessDAO.getTopicDao();
    }

    @Test
    public void addTopic() {
        Topic topic1 = new Topic("1", "2", "3", "4", "5");
        Topic topic2 = new Topic("2", "3", "4", "5", "6");
        topicDao.addTopic(topic1);
        topicDao.addTopic(topic2);
    }

    @Test
    public void findTopics() {
        List<Topic> topicList = topicDao.findTopics();
        topicList.forEach(System.out::print);
    }

    @Test
    public void testAnalyseTopic() {
//        SimpleFrequentCount.topicAnalyse();
    }

    @Test
    public void testFindOneTopic() {
        String topicName = "#HarryStylesTODAY";
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        Topic topic = topicDao.findTopic(topicName);
        System.out.println();
    }
}
