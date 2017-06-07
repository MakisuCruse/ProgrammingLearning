package core.DAO.Impl;

import core.DAO.ITopicDAO;
import core.model.Topic;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by makisucruse on 2017/6/6.
 */
public class TopicDaoImpl extends JdbcDaoSupport implements ITopicDAO {
    @Override
    public void addTopic(Topic topic) {
        try {
            String sql = "insert into topic(topicName,description,topicType,online_addr,local_addr) VALUES (?,?,?,?,?)";
            this.getJdbcTemplate().update(sql, topic.getTopicName(), topic.getDescription(), topic.getTopicType(), topic.getOnlineUrl(), topic.getLocalUrl());
        } catch (DuplicateKeyException e) {
            String sql = "update topic set description=?,topicType=?,online_addr=?,local_addr=? where topicName=?";
            this.getJdbcTemplate().update(sql, topic.getDescription(), topic.getTopicType(), topic.getOnlineUrl(), topic.getLocalUrl(), topic.getTopicName());
        }
    }

    @Override
    public List<Topic> findTopics() {
        String sql = "select * from topic";
        return this.getJdbcTemplate().query(sql, new TopicRowMapper());
    }

    @Override
    public Topic findTopic(String topic) {
        String sql = "select * from topic where topicName=?";
        return this.getJdbcTemplate().queryForObject(sql, new OneTopicRowMapper(), topic);
    }
}

class TopicRowMapper implements RowMapper<Topic> {

    @Override
    public Topic mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Topic(resultSet.getString("topicName"), resultSet.getString("description"), resultSet.getString("topicType"), resultSet.getString("online_addr"), resultSet.getString("local_addr"));
    }
}

class OneTopicRowMapper implements RowMapper<Topic> {

    @Override
    public Topic mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Topic(resultSet.getString("topicName"), resultSet.getString("description"), resultSet.getString("topicType"), resultSet.getString("online_addr"), resultSet.getString("local_addr"));
    }
}