package core.DAO.Impl;

import core.DAO.ITweetDAO;
import core.model.Tweet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by makisucruse on 2017/3/22.
 */
public class TweetDaoImpl extends JdbcDaoSupport implements ITweetDAO {
    private final int perPage = 50;

    public boolean addTweet(Tweet tweet) {
        String sql = "INSERT INTO tweet(tweet_id,user_id,text_info,trend_name,create_at,latitude,longitude) VALUES(?,?,?,?,?,?,?)";
        try {
            this.getJdbcTemplate().update(sql, tweet.getTweetId(), tweet.getUserId(), tweet.getText(), tweet.getTrendName(), tweet.getCreateAt(), tweet.getLatitude(), tweet.getLongtitude());
        } catch (DuplicateKeyException ignored) {
            System.out.println("catch DuplicationKeyException");
            return false;
        } finally {
            return true;
        }
    }

    public void deleteTweet(String tweetId) {

    }

    public void updateTweet(Tweet tweet) {

    }

    public List<Tweet> findTrendTweet(String trend) {
        String sql = "SELECT * FROM tweet WHERE trend_name=\"" + trend + "\"";
        return this.getJdbcTemplate().query(sql, new TweetRowMapper());
    }

    public List<Tweet> findTweet(int page) {
        int startPage = (page - 1) * perPage;
        String sql = "select * from tweet where id>" + startPage + " and id<=" + (startPage + perPage);
        return this.getJdbcTemplate().query(sql, new TweetRowMapper());
    }

    public Tweet findTweet(String tweetId) {
        String sql = "select * from tweet where tweet_id=?";
        return this.getJdbcTemplate().queryForObject(sql, new TweetRowMapper(), tweetId);
    }

    public List<String> findInTweetNotInTopic() {
        String sql = "select distinct trend_name from tweet where trend_name not in (select topicName from topic)";
        return this.getJdbcTemplate().query(sql, new TrendRowMapper());
    }

    public List<String> findTrends() {
        String sql = "SELECT DISTINCT trend_name FROM tweet ";
        return this.getJdbcTemplate().query(sql, new TrendRowMapper());
    }

    public List<String> findEnoughTrends() {
        String sql = "SELECT DISTINCT trend_name FROM tweet group by trend_name having count(*) >1000";
        return this.getJdbcTemplate().query(sql, new TrendRowMapper());
    }

    public List<String> findNearTwoDayTrends() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date threeDayAgo = calendar.getTime();
        String date = df.format(threeDayAgo);
        String sql = "select distinct trend_name from tweet where create_at>? group by trend_name having count(*)>500";
        return this.getJdbcTemplate().query(sql, new TrendRowMapper(), date);
    }

    public List<String> findTodayTrends() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        String sql = "select distinct trend_name from tweet where create_at>'" + date + "'";
        return this.getJdbcTemplate().query(sql, new TrendRowMapper());
    }

    public List<Integer> findPerHourTweetCount(String trendName) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        String dateStart, dateStop;
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            dateStart = date + " 0" + i + ":00:00";
            dateStop = date + " 0" + (i + 1) + ":00:00";
            String sql = "select count(*) from tweet where trend_name = \"" + trendName + "\" and create_at > \"" + dateStart + "\" and create_at < \"" + dateStop + "\"";
            ret.add(this.getJdbcTemplate().queryForObject(sql, Integer.class));
        }
        return ret;
    }

    public List<Tweet> findAllTweet() {
        String sql = "select * from tweet";
        return this.getJdbcTemplate().query(sql, new TweetRowMapper());
    }
}

class TrendRowMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString("trend_name");
    }
}


class TweetRowMapper implements RowMapper<Tweet> {

    public Tweet mapRow(ResultSet resultSet, int i) throws SQLException {
        Tweet t = new Tweet();
        t.setTweetId(resultSet.getString("tweet_id"));
        t.setUserId(resultSet.getString("user_id"));
        t.setText(resultSet.getString("text_info"));
        t.setTrendName(resultSet.getString("trend_name"));
        t.setCreateAt(resultSet.getDate("create_at"));
        t.setLatitude(resultSet.getString("latitude"));
        t.setLongtitude(resultSet.getString("longitude"));
        return t;
    }
}

