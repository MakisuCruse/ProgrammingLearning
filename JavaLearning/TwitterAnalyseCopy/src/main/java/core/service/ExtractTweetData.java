package core.service;

import core.Algo.SimpleFrequentCount;
import core.DAO.ITweetDAO;
import core.DAO.IUserDAO;
import core.DAO.Impl.TweetDaoImpl;
import core.DAO.Impl.UserDaoImpl;
import core.model.QueueData;
import core.model.Tweet;
import org.springframework.jms.core.JmsTemplate;
import twitter4j.*;
import util.AccessDAO;
import util.OAuth;
import util.TrendPictureProcess;
import util.URLConnectionDownloader;
import web.websocket.QueueSender;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by makisucruse on 2017/3/22.
 */
public class ExtractTweetData {
    private final static int placeNum = 23424977;
    private static boolean isRunning = true;
    private static Map<String, Boolean> trendShouldDownload = new HashMap<>();
    private static ExecutorService service = Executors.newCachedThreadPool();
    private static String staticResourcePath = "/Users/makisucruse/Downloads/userInfo/";
    private static TweetDaoImpl tweetDao = (TweetDaoImpl) AccessDAO.getTweetDao();
    private static UserDaoImpl userDao = (UserDaoImpl) AccessDAO.getUserDao();
    private static JmsTemplate jms = (JmsTemplate) AccessDAO.getTwitterMsg();
    private static int account = 0;

    public static void main(String[] args) throws Exception {
        WrapperAnalyse(Type.existTrend);
    }

    static {
        init();
    }

    private static void stop() {
        isRunning = false;
    }

    private static void WrapperAnalyse(Type t) throws Exception {
        try {
            while (isRunning) {
                //更新trend下载列表
                //这里是否需要删除多余的图片?
                init();
                if (account == 2) account = 0;
                if (t.equals(Type.existTrend)) {
                    getExistTrend(account++);
                } else if (t.equals(Type.twiiterTrend)) {
                    getDataFromTweet(account++);
                } else if (t.equals(Type.todayTrend)) {
                    getTodayDataFromTweet(account++);
                }
            }
        } catch (TwitterException e) {
            if (account == 2) {
                System.out.println("sleep ten minute,then continue");
                //更新有足够话题量的 三日内大于500推文的话题
                service.submit((Runnable) () -> SimpleFrequentCount.topicAnalyseThreeDay(TweetService.findNearTwoDayTopics()));
                //添加新获取的话题进入Topic的解释入库
                service.submit((Runnable) SimpleFrequentCount::topicAnalysePerDrag);
                //处理trend中的冗余图片,不断更新更好的图片
                service.submit((Runnable) TrendPictureProcess::process);
                Thread.sleep(1000 * 60 * 10);
                account = 0;
                WrapperAnalyse(t);
            }
            System.out.println(account + "切换账号");
            Thread.sleep(1000 * 60);
            WrapperAnalyse(t);
        } finally {
            service.shutdown();
        }
    }

    private static void getTwitterFromTrendName(ITweetDAO tweetDAO, IUserDAO userDAO, String trendName, Twitter twitter, Query query) throws Exception {
        QueryResult result = twitter.search(query);
        List<Status> statuses = result.getTweets();
        int i = 0;
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
            service.submit((Runnable) () -> {
                try {
                    URLConnectionDownloader.download(twitterUser.getOriginalProfileImageURL(), staticResourcePath + "/images/" + user.getUserId() + ".png");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (trendShouldDownload.get(trendName) == null) {
                if (shouldDownload(trendName)) {
                    trendShouldDownload.put(trendName, true);
                    if (downloadMediaPhoto(s, trendName, i)) {
                        i++;
                    }
                } else {
                    trendShouldDownload.put(trendName, false);
                }
            } else if (trendShouldDownload.get(trendName)) {
                if (shouldDownload(trendName)) {
                    if (downloadMediaPhoto(s, trendName, i)) {
                        i++;
                    }
                } else {
                    trendShouldDownload.put(trendName, false);
                }
            }
            user.setImage(twitterUser.getOriginalProfileImageURL());
            user.setFollowerCount(twitterUser.getFollowersCount());
            user.setUserStatusCount(twitterUser.getStatusesCount());
            user.setLocation(twitterUser.getLocation());
            user.setFriendCount(twitterUser.getFriendsCount());
            user.setFavoriteCount(twitterUser.getFavouritesCount());
            if (tweetDAO.addTweet(tweet)) {
                QueueData data = new QueueData(user, tweet);
                QueueSender.sendMqMessage(jms, data);
            }
            userDAO.addUser(user);
        }
    }

    private static void getExistTrend(int account) throws Exception {
        List<String> trends = tweetDao.findTrends();
        Twitter twitter = OAuth.oAuthTwitter(account);
        for (String t : trends) {
            Query query = new Query(t);
            query.setCount(100);
            query.setLang("en");
            getTwitterFromTrendName(tweetDao, userDao, t, twitter, query);
        }
    }

    private static void getDataFromTweet(int account) throws Exception {
        Twitter twitter = OAuth.oAuthTwitter(account);
        Trend[] trends = twitter.getPlaceTrends(placeNum).getTrends();
        for (Trend t : trends) {
            String queryUrl = t.getQuery();
            Query query = new Query(queryUrl);
            query.setLang("en");
            query.setCount(100);
            getTwitterFromTrendName(tweetDao, userDao, t.getName(), twitter, query);
        }
    }

    private static void getTodayDataFromTweet(int account) throws Exception {
        Twitter twitter = OAuth.oAuthTwitter(account);
        List<String> trends = tweetDao.findTodayTrends();
        for (String t : trends) {
            Query query = new Query(t);
            query.setLang("en");
            query.setCount(100);
            getTwitterFromTrendName(tweetDao, userDao, t, twitter, query);
        }
    }

    private static void getDataThroughTrendName(int account, String trendName) throws Exception {
        Twitter twitter = OAuth.oAuthTwitter(account);
        Query query = new Query(trendName);
        query.setLang("en");
        query.setCount(100);
        getTwitterFromTrendName(tweetDao, userDao, trendName, twitter, query);
    }

    private enum Type {
        existTrend("existTrend"), twiiterTrend("trend"), todayTrend("todayTrend");

        private String s;

        Type(String s) {
            this.s = s;
        }

    }

    private static void init() {
        String fileLocation = staticResourcePath + "trend/";
        File file = new File(fileLocation);
        for (File f : file.listFiles()) {
            if (!shouldDownload(f.getName())) trendShouldDownload.put(f.getName(), false);
            else trendShouldDownload.put(f.getName(), true);
        }
    }

    public static boolean shouldDownload(String trendName) {
        String fileLocation = staticResourcePath + "/trend/" + trendName + "/";
        File file = new File(fileLocation);
        if (!file.exists()) file.mkdir();
        return file.listFiles() != null && file.listFiles().length < 3;
    }

    private static boolean downloadMediaPhoto(Status status, String trendName, int i) {
        System.out.println(i);
        MediaEntity[] mediaEntities = status.getMediaEntities();
        if (mediaEntities.length == 0) return false;
        for (MediaEntity m : mediaEntities) {
            System.out.println("1..");
            try {
                service.submit((Runnable) () -> {
                    try {
                        URLConnectionDownloader.download(m.getMediaURL(), staticResourcePath + "/trend/" + trendName + "/" + i + ".png");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("2..");
        return true;
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


