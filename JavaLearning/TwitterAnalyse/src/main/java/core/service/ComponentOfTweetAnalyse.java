package core.service;

import core.model.Tweet;
import util.TrendConcerning;

import java.util.*;

/**
 * Created by makisucruse on 2017/4/14.
 */
public class ComponentOfTweetAnalyse {
    public static void main(String[] args) {
        Map<String, Map<String, List<String>>> ret = getComponentOfTweet("Wenger");
        for (Map.Entry<String, Map<String, List<String>>> m : ret.entrySet()) {
            System.out.println(m.getKey());
            for (Map.Entry<String, List<String>> m1 : m.getValue().entrySet()) {
                System.out.println(m1.getKey() + ":" + m1.getValue());
            }
            System.out.println("======");
        }
    }

    private static Map<String, Map<String, List<String>>> getComponentOfTweet(String trend) {
        Map<String, Map<String, List<String>>> ret = new HashMap<>();
        List<String> highFrequentVerbs = TrendConcerning.getHighFrequentVerb(trend);
        String[] highFrequentWords = TrendConcerning.getFrequentWords(trend).split(" ");
        List<String> preVerbs = new ArrayList<>();
        List<String> postVerbs = new ArrayList<>();
        List<Tweet> tweets = TrendConcerning.getTrendTweets(trend);
        for (String verb : highFrequentVerbs) {
            for (Tweet tweet : tweets) {
                String text = tweet.getText();
                int index;
                if ((index = text.indexOf(verb)) != -1) {
                    String[] pre = text.substring(0, index).split(" ");
                    String[] post = text.substring(index, text.length()).split(" ");
                    if (pre.length != 0) {
                        for (String preWord : pre) {
                            for (String s : highFrequentWords) {
                                if (preWord.equals(s) && !preVerbs.contains(s)) preVerbs.add(preWord);
                            }
                        }
                    }
                    if (post.length != 1) {
                        for (int i = 1; i < post.length; i++) {
                            for (String s : highFrequentWords) {
                                if (post[i].equals(s) && !postVerbs.contains(s)) postVerbs.add(s);
                            }
                        }
                    }
                }
            }
            Map<String, List<String>> map = new HashMap<>();
            map.put("preMap", preVerbs);
            map.put("postMap", postVerbs);
            ret.put(verb, map);
        }
        return ret;
    }

    private static Map<String, Map<String, List<String>>> getResult(String trend) {
        Map<String, Map<String, List<String>>> ret = new HashMap<>();
        List<String> highFrequentVerbs = TrendConcerning.getHighFrequentVerb(trend);
        String[] highFrequentWords = TrendConcerning.getFrequentWords(trend).split(" ");
        List<String> preVerbs = new ArrayList<>();
        List<String> postVerbs = new ArrayList<>();
        List<Tweet> tweets = TrendConcerning.getTrendTweets(trend);
        for (String verb : highFrequentVerbs) {
            for (Tweet t : tweets) {
                String text = t.getText();
                if (text.contains(verb)) {
                    String[] sentences = text.split("[.!?]");
                    for (String perSen : sentences) {
                        int index1;
                        if ((index1 = perSen.indexOf(verb)) != -1) {
                            String[] pre = perSen.substring(0, index1).split(" ");
                            String[] post = perSen.substring(index1, perSen.length()).split(" ");
                            if (pre.length != 0) {
                                for (String preWord : pre) {
                                    for (String s : highFrequentWords) {
                                        if (preWord.equals(s) && !preVerbs.contains(s)) preVerbs.add(preWord);
                                    }
                                }
                            }
                            if (post.length != 1) {
                                for (int i = 1; i < post.length; i++) {
                                    for (String s : highFrequentWords) {
                                        if (post[i].equals(s) && !postVerbs.contains(s)) postVerbs.add(s);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Map<String, List<String>> map = new HashMap<>();
            map.put("preMap", preVerbs);
            map.put("postMap", postVerbs);
            ret.put(verb, map);
        }
        return ret;
    }
}
