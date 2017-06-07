package core.DAO;

import core.model.Topic;

import java.util.List;

/**
 * Created by makisucruse on 2017/6/6.
 */
public interface ITopicDAO {
    void addTopic(Topic topic);

    List<Topic> findTopics();

    Topic findTopic(String topic);
}
