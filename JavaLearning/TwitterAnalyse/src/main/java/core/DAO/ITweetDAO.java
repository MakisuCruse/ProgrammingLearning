package core.DAO;

import core.model.Tweet;

import java.util.List;

/**
 * Created by makisucruse on 2017/3/22.
 */
public interface ITweetDAO {
    boolean addTweet(Tweet tweet);

    void deleteTweet(String tweetId);

    void updateTweet(Tweet tweet);

    List<Tweet> findTrendTweet(String trend);
}
