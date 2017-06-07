package core.DAO.Impl;

import core.DAO.IUserDAO;
import core.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by makisucruse on 2017/5/9.
 */
public class UserDaoImpl extends JdbcDaoSupport implements IUserDAO {
    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO user(user_id,screen_name,description,time_zone,image_original,follower_count,user_status_count,location,friend_count,favorite_count) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            this.getJdbcTemplate().update(sql, user.getUserId(), user.getScreenName(), user.getDescription(), user.getTimeZone(), user.getImage(), user.getFollowerCount(), user.getUserStatusCount(), user.getLocation(), user.getFriendCount(), user.getFavoriteCount());
        } catch (DuplicateKeyException e) {
        }
    }

    @Override
    public List<User> findUsers() {
        String sql = "SELECT * FROM user";
        return this.getJdbcTemplate().query(sql, new UserRowMapper());
    }

    @Override
    public List<User> findUser(String userId) {
        String sql = "select * from user where user_id=" + userId;
        return this.getJdbcTemplate().query(sql, new UserRowMapper());
    }

    public List<User> findUserThroughScreenName(String screenName) {
        String sql = "select * from user where screen_name=\"" + screenName + "\"";
        return this.getJdbcTemplate().query(sql, new UserRowMapper());
    }
}

class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getString("user_id"));
        user.setScreenName(resultSet.getString("screen_name"));
        user.setDescription(resultSet.getString("description"));
        user.setTimeZone(resultSet.getString("time_zone"));
        user.setImage(resultSet.getString("image_original"));
        user.setFollowerCount(resultSet.getInt("follower_count"));
        user.setUserStatusCount(resultSet.getInt("user_status_count"));
        user.setLocation(resultSet.getString("location"));
        user.setFriendCount(resultSet.getInt("friend_count"));
        user.setFavoriteCount(resultSet.getInt("favorite_count"));
        return user;
    }
}

