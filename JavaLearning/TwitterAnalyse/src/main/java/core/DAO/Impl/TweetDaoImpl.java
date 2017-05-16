package core.DAO.Impl;

import core.DAO.ITweetDAO;
import core.model.Tweet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

    public List<String> findTrends() {
        String sql = "SELECT DISTINCT trend_name FROM tweet";
        return this.getJdbcTemplate().query(sql, new TrendRowMapper());
    }

    public List<String> findTodayTrends() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        String sql = "select distinct trend_name from tweet where create_at>'" + date + "'";
        return this.getJdbcTemplate().query(sql, new TrendRowMapper());
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