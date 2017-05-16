package core.Algo;

import core.DAO.Impl.TweetDaoImpl;
import core.DAO.Impl.UserDaoImpl;
import core.model.Tweet;
import core.model.User;
import util.AccessDAO;
import util.ConvertForR;
import util.DataPreProcessors;
import util.WriteFile;

import java.util.*;

/**
 * Created by makisucruse on 2017/4/20.
 */
public class SimpleFrequentCount {

    public static void main(String[] args) {
<<<<<<< HEAD
<<<<<<< HEAD
        String trend = "LaVista Rd";
        System.out.println(getTodayTrends().size());
        System.out.println(getExistTrends().size());
=======
=======
>>>>>>> new
        String trend = "American Idol";
        for (Map.Entry<String, Integer> m : getResultDependsOnScore(trend)) {
            System.out.println(m.getKey() + ";" + m.getValue());
        }
//        System.out.println(getTodayTrends().size());
//        System.out.println(getExistTrends().size());

<<<<<<< HEAD
>>>>>>> new
=======
>>>>>>> new
//        entryFunc(trend);
//        for (Map.Entry<String, Integer> m : getResult("#4WordLetDowns")) {
//            System.out.println(m.getKey() + ";" + m.getValue());
//        }
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
        return strs.length < 3 || getUser(tweet.getUserId()).getUserStatusCount() < 10;
    }

    private static String[] getAllText(String trend) {
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        List<Tweet> tweets = dao.findTrendTweet(trend);
        StringBuilder builder = new StringBuilder();
        for (Tweet t : tweets) {
            if (shouldBeIgnore(t)) continue;
            builder.append(t.getText()).append(" ");
        }
        return builder.toString().split(" ");
    }

    private static User getUser(String userId) {
        UserDaoImpl dao = (UserDaoImpl) AccessDAO.getUserDao();
        return dao.findUser(userId).get(0);
    }

    private static List<Tweet> getTweets(String trend) {
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        return dao.findTrendTweet(trend);
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
            sentenceScore.put(m.getKey(), m.getValue());
        }
        return sortMap(sentenceScore);
    }

    private static void show(String trend) {
        List<Map.Entry<String, Integer>> result = getResultDependsOnScore(trend);

        for (Map.Entry<String, Integer> m : result) {
            System.out.println("[" + process(m.getKey()) + "," + m.getValue() + "]");
        }
    }

    private static String process(String text) {
        text = text.replaceAll("\n", " ");
        String[] tmp = text.split(":");
        if (tmp.length == 1) {
            return tmp[0];
        } else {
            return tmp[1];
        }
    }

    private static List<Map.Entry<String, Integer>> getResultDependOnFollowers(String trend) {
        Map<String, Integer> sentenceScore = new HashMap<>();
        for (Tweet t : getTweets(trend)) {
            sentenceScore.put(t.getText(), getUser(t.getUserId()).getFollowerCount());
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

    public static List<String> getTodayTrends() {
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        return dao.findTodayTrends();
    }

    public static List<String> getExistTrends() {
        TweetDaoImpl dao = (TweetDaoImpl) AccessDAO.getTweetDao();
        return dao.findTrends();
    }
}
