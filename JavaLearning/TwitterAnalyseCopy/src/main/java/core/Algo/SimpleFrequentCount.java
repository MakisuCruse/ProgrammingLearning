package core.Algo;

import com.alibaba.fastjson.JSON;
import core.DAO.Impl.TopicDaoImpl;
import core.DAO.Impl.TweetDaoImpl;
import core.DAO.Impl.UserDaoImpl;
import core.model.Description;
import core.model.Topic;
import core.model.Tweet;
import core.model.User;
import util.*;

import java.util.*;

/**
 * Created by makisucruse on 2017/4/20.
 */
public class SimpleFrequentCount {
    private static TweetDaoImpl tweetDao = (TweetDaoImpl) AccessDAO.getTweetDao();
    private static UserDaoImpl userDao = (UserDaoImpl) AccessDAO.getUserDao();
    private static TopicDaoImpl topicDao = (TopicDaoImpl) AccessDAO.getTopicDao();

    public static void main(String[] args) {
        //全局更新
        //List<String> trends = tweetDao.findTrends();
        //具有足够的数据集的话题更新解释
        List<String> trends = tweetDao.findEnoughTrends();
        trends.parallelStream().forEach(x -> topicDao.addTopic(topicAnalyse(x)));
    }

    public static void topicAnalysePerDrag() {
        List<String> trends = tweetDao.findInTweetNotInTopic();
        trends.parallelStream().forEach(x -> topicDao.addTopic(topicAnalyse(x)));
    }

    public static void topicAnalyseThreeDay(List<String> topics) {
        topics.forEach(x -> topicDao.addTopic(topicAnalyse(x)));
    }

    public static Topic topicAnalyse(String trendName) {
        List<Description> descriptions = getResultDependsOnScore1(trendName);
        String localUrl;
        if (trendName.startsWith("#")) {
            localUrl = "%23" + trendName.substring(1);
            localUrl = "/userinfo/trend/" + localUrl + "/t0.png";
        } else {
            localUrl = "/trend/" + trendName + "/t0.png";
        }
        return new Topic(trendName, JSON.toJSONString(descriptions), null, null, localUrl);
    }

    private static void entryFunc(String trend) {
        List<String> ret = getResultDependsOnHashtag(trend);
        if (ret.size() != 0) {
            ret.forEach(System.out::println);
        } else {
            for (Map.Entry<String, Integer> m : getResultDependsOnScore(trend)) {
                System.out.println(m.getKey() + ";" + m.getValue());
            }
            System.out.println("===========");
//            for (Map.Entry<String, Integer> m : getResultDependOnFollowers(trend)) {
//                System.out.println(m.getKey() + ";" + m.getValue());
//            }
        }
    }

    public static List<Integer> getTodayPerHourTweetCount(String trend) {
        TweetDaoImpl tweetDao = (TweetDaoImpl) AccessDAO.getTweetDao();
        return tweetDao.findPerHourTweetCount(trend);
    }

    //trend==>>按照词频排序的高频词
    public static List<Map.Entry<String, Integer>> getResult(String trend) {
        return analyseTrend(getAllText(trend), trend);
    }

    private static Map<List<String>, List<Integer>> getTopWords(String trend) {
        return getTopFrequentWords(analyseTrend(getAllText(trend), trend));
    }

    private static Map<List<String>, List<Integer>> getTopFrequentWords(List<Map.Entry<String, Integer>> lst) {
        Map<List<String>, List<Integer>> ret = new HashMap<>();
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (Map.Entry<String, Integer> m : lst) {
            keys.add(m.getKey());
            values.add(m.getValue());
        }
        ret.put(keys.subList(0, Math.min(keys.size(), 20)), values.subList(0, Math.min(keys.size(), 20)));
        return ret;
    }

    private static boolean shouldBeIgnore(Tweet tweet) {
        String[] strs = tweet.getText().split(" ");
        User user = getUser(tweet.getUserId());
        if (user != null)
            return strs.length < 3 || user.getUserStatusCount() < 10;
        return strs.length < 3;
    }

    private static void simpleWordsWrite(String trend) {
        String fileName = "/Users/makisucruse/Downloads/OutPutFile1/" + trend + ".txt";
        WriteFile.writeMethod(fileName, ConvertForR.convert(analyseTrend(getAllText(trend), trend)));
    }

    private static List<Map.Entry<String, Integer>> analyseTrend(String[] texts, String trend) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String text : texts) {
            text = DataPreProcessors.truncationText(text);
            if (DataPreProcessors.shouldBeIgnore(text, trend)) continue;
            if (!wordCount.containsKey(text)) {
                wordCount.put(text, 1);
            } else {
                wordCount.put(text, wordCount.get(text) + 1);
            }
        }
        return sortMap(wordCount);
    }

    private static List<Map.Entry<String, Integer>> sortMap(Map<String, Integer> wordCount) {
        List<Map.Entry<String, Integer>> tmp = new ArrayList<>(wordCount.entrySet());
        Collections.sort(tmp, (o1, o2) -> o2.getValue() - o1.getValue());
        return tmp;
    }

    private static List<Map.Entry<String, Integer>> subMap(Map<String, Integer> map) {
        int size = 0;
        Map<String, Integer> tmp = new HashMap<>();
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            if (size == 5) {
                break;
            }
            size++;
            tmp.put(m.getKey(), m.getValue());
        }
        List<Map.Entry<String, Integer>> tmp1 = new ArrayList<>(tmp.entrySet());
        Collections.sort(tmp1, (o1, o2) -> o2.getValue() - o1.getValue());
        return tmp1;
    }

    public static Map<String, List<Map.Entry<String, Integer>>> getAllResultDependsOnScore() {
        Map<String, List<Map.Entry<String, Integer>>> ret = new HashMap<>();
        List<String> trends = getAllTrends();
        trends.forEach(x -> ret.put(x, getResultDependsOnScore(x)));
        return ret;
    }

    public static List<Description> getResultDependsOnScore1(String trendName) {
        List<Map.Entry<String, Integer>> ret = getResultDependsOnScore(trendName);
        List<Description> descriptionList = new ArrayList<>();
        ret.forEach(x -> descriptionList.add(new Description(x.getKey(), x.getValue())));
        return descriptionList;
    }

    public static List<Map.Entry<String, Integer>> getResultDependsOnScore(String trend) {
        Map<List<String>, List<Integer>> ret = getTopWords(trend);
        Map<String, Integer> sentenceScore = new HashMap<>();
        for (Map.Entry<List<String>, List<Integer>> m : ret.entrySet()) {
            List<String> keys = m.getKey();
            List<Integer> values = m.getValue();
            Integer count = 0;
            for (Integer i : values) {
                count += i;
            }
            for (int i = 0; i < values.size(); i++) {
                values.set(i, values.get(i) * 100 / count);
            }
            for (Tweet t : getTweets(trend)) {
                int score = 0;
                String[] strs = t.getText().split(" ");
                for (String s : strs) {
                    for (int i = 0; i < keys.size(); i++) {
                        if (s.equals(keys.get(i))) {
                            score += values.get(i);
                        }
                    }
                }
                sentenceScore.put(t.getText(), score);
            }
        }
        List<Map.Entry<String, Integer>> subMap = subMap(sentenceScore);
        sentenceScore.clear();
        for (Map.Entry<String, Integer> m : subMap) {
            sentenceScore.put(PostProcess.eliminate(m.getKey()), m.getValue());
        }
        return sortMap(sentenceScore);
    }

    private static void show(String trend) {
        List<Map.Entry<String, Integer>> result = getResultDependsOnScore(trend);

        for (Map.Entry<String, Integer> m : result) {
            System.out.println("[" + m.getKey() + "," + m.getValue() + "]");
        }
    }

    private static List<Map.Entry<String, Integer>> getResultDependOnFollowers(String trend) {
        Map<String, Integer> sentenceScore = new HashMap<>();
        for (Tweet t : getTweets(trend)) {
            User user = getUser(t.getUserId());
            if (user != null) {
                sentenceScore.put(t.getText(), user.getFollowerCount());
            }
            sentenceScore.put(t.getText(), -1);
        }
        return subMap(sentenceScore);
    }


    private static List<String> getResultDependsOnHashtag(String trendName) {
        List<String> ret = new ArrayList<>();
        for (Tweet t : getTweets(trendName)) {
            String[] strs = t.getText().split(" ");
            for (int i = 0; i < strs.length; i++) {
                if (i < strs.length - 1 && strs[i].equals(trendName) && strs[i + 1].equals("hashtag")) {
                    ret.add(t.getText());
                }
            }
        }
        for (int i = ret.size() - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (ret.get(j).equals(ret.get(i))) {
                    ret.remove(j);
                    break;
                }
            }
        }
        return ret;
    }

    private static String[] getAllText(String trend) {
        List<Tweet> tweets = tweetDao.findTrendTweet(trend);
        StringBuilder builder = new StringBuilder();
        for (Tweet t : tweets) {
            if (shouldBeIgnore(t)) continue;
            builder.append(t.getText()).append(" ");
        }
        return builder.toString().split(" ");
    }

    private static User getUser(String userId) {
        List<User> users = userDao.findUser(userId);
        if (users.size() != 0) {
            return userDao.findUser(userId).get(0);
        }
        return null;
    }

    private static List<Tweet> getTweets(String trend) {
        return tweetDao.findTrendTweet(trend);
    }

    private static List<String> getAllTrends() {
        return tweetDao.findTrends();
    }

    public static List<String> getTodayTrends() {
        return tweetDao.findTodayTrends();
    }

    public static List<String> getExistTrends() {
        return tweetDao.findTrends();
    }
}
