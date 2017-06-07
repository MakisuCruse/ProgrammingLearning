package core.model;

import java.io.Serializable;

/**
 * Created by makisucruse on 2017/5/16.
 */
public class QueueData implements Serializable {
    private User user;
    private Tweet tweet;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public QueueData(User user, Tweet tweet) {
        this.user = user;
        this.tweet = tweet;
    }

    @Override
    public String toString() {
        return "QueueData{" +
                "tweet=" + tweet +
                ", user=" + user +
                '}';
    }
}
