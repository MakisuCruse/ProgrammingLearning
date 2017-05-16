package core.service;

import core.DAO.ITweetDAO;
import core.DAO.IUserDAO;
import core.DAO.Impl.TweetDaoImpl;
import core.DAO.Impl.UserDaoImpl;
import core.model.Tweet;
import org.springframework.jms.core.JmsTemplate;
import twitter4j.*;
import util.AccessDAO;
import util.OAuth;
import util.URLConnectionDownloader;
import web.websocket.QueueSender;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makisucruse on 2017/3/22.
 */
public class ExtractTweetData {
    private final static int placeNum = 23424977;
    private static boolean isRunning = true;

    public static void main(String[] args) throws Exception {
        WrapperAnalyse(Type.existTrend);
    }

    private static void stop() {
        isRunning = false;
    }

    private static void WrapperAnalyse(Type t) throws Exception {
        int account = 4;
        while (isRunning) {
//            if (account == 6) Thread.sleep(60 * 1000 * 2);
            System.out.println("sleep after one call of two account ");
            ExtractTweetData analyse = new ExtractTweetData();
            if ((account = account / 3) == 2) account = 0;
            if (t.equals(Type.existTrend)) {
                analyse.getExistTrend(account++);
            } else if (t.equals(Type.twiiterTrend)) {
                analyse.getDataFromTweet(account++);
//                analyse.getDataThroughTrendName(account++, "#MSAD");
            } else {
                throw new RuntimeException("No support operation");
            }
        }
    }

    private static void getTwitterFromTrendName(ITweetDAO tweetDAO, IUserDAO userDAO, String trendName, Twitter twitter, Query query) throws Exception {
        JmsTemplate jms = (JmsTemplate) AccessDAO.getTwitterMsg();
        QueryResult result = twitter.search(query);
        List<Status> statuses = result.getTweets();
        for (Status s : statuses) {
            Tweet tweet = new Tweet();
            twitter4j.User twitterUser = s.getUser();
            tweet.setTweetId(String.valueOf(s.getId()));
            tweet.setUserId(String.valueOf(twitterUser.getId()));
            tweet.setText(s.getText());
            tweet.setTrendName(trendName);
            tweet.setCreateAt(s.getCreatedAt());
            if (s.getGeoLocation() != null) {
                tweet.setLatitude(String.valueOf(s.getGeoLocation().getLatitude()));
                tweet.setLongtitude(String.valueOf(s.getGeoLocation().getLongitude()));
            }
            core.model.User user = new core.model.User();
            user.setUserId(String.valueOf(twitterUser.getId()));
            user.setScreenName(twitterUser.getScreenName());
            user.setDescription(twitterUser.getDescription());
            user.setTimeZone(twitterUser.getTimeZone());
            //获得了图的url,对应获得流
            try {
                URLConnectionDownloader.download(twitterUser.getOriginalProfileImageURL(), "/Users/makisucruse/Downloads/tweetPhoto/" + user.getUserId() + ".png");
            } catch (FileNotFoundException e) {

            }
            user.setImage(twitterUser.getOriginalProfileImageURL());
            user.setFollowerCount(twitterUser.getFollowersCount());
            user.setUserStatusCount(twitterUser.getStatusesCount());
            user.setLocation(twitterUser.getLocation());
            user.setFriendCount(twitterUser.getFriendsCount());
            user.setFavoriteCount(twitterUser.getFavouritesCount());
            if (tweetDAO.addTweet(tweet)) {
                QueueSender.sendMqMessage(jms, null, tweet.getText());
            }
            userDAO.addUser(user);
        }
    }

    private void getExistTrend(int account) throws Exception {
        TweetDaoImpl tweetDao = (TweetDaoImpl) AccessDAO.getTweetDao();
        UserDaoImpl userDao = (UserDaoImpl) AccessDAO.getUserDao();
        List<String> trends = tweetDao.findTrends();
        Twitter twitter = OAuth.oAuthTwitter(account);
        for (String t : trends) {
            Query query = new Query(t);
            query.setCount(100);
            query.setLang("en");
            query.setResultType(Query.ResultType.recent);
            getTwitterFromTrendName(tweetDao, userDao, t, twitter, query);
        }
    }

    private void getDataFromTweet(int account) throws Exception {
        ITweetDAO tweetDAO = AccessDAO.getTweetDao();
        IUserDAO userDAO = AccessDAO.getUserDao();
        Twitter twitter = OAuth.oAuthTwitter(account);
        Trend[] trends = twitter.getPlaceTrends(placeNum).getTrends();

        for (Trend t : trends) {
            String queryUrl = t.getQuery();
            Query query = new Query(queryUrl);
            query.setLang("en");
            query.setCount(100);
            query.setResultType(Query.ResultType.recent);
            getTwitterFromTrendName(tweetDAO, userDAO, t.getName(), twitter, query);
        }
    }

    private void getDataThroughTrendName(int account, String trendName) throws Exception {
        ITweetDAO tweetDAO = AccessDAO.getTweetDao();
        IUserDAO userDAO = AccessDAO.getUserDao();
        Twitter twitter = OAuth.oAuthTwitter(account);
        Query query = new Query(trendName);
        query.setLang("en");
        query.setCount(100);
        getTwitterFromTrendName(tweetDAO, userDAO, trendName, twitter, query);
    }

    enum Type {
        existTrend("existTrend"), twiiterTrend("trend");

        private String s;

        Type(String s) {
            this.s = s;
        }

    }

    public static List<String> getTrendsRightNow() throws TwitterException {
        Twitter twitter = OAuth.oAuthTwitter(1);
        Trend[] trends = twitter.getPlaceTrends(placeNum).getTrends();
        List<String> lst = new ArrayList<>();
        for (Trend s : trends) {
            lst.add(s.getName());
        }
        return lst;
    }
}


