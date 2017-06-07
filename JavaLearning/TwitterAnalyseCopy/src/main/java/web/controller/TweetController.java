package web.controller;

import core.Algo.SimpleFrequentCount;
import core.model.Topic;
import core.service.TweetService;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by makisucruse on 2017/5/11.
 */
@RestController
@RequestMapping(value = "/tweet/")
public class TweetController {
    @ResponseBody
    @RequestMapping(value = "/trend/all", method = RequestMethod.GET)
    public List<String> getTrendsExistsInSystem() {
        return SimpleFrequentCount.getExistTrends();
    }

    @ResponseBody
    @RequestMapping(value = "/trend/today")
    public List<String> getTrendsRightNow() throws TwitterException {
        return SimpleFrequentCount.getTodayTrends();
    }

    @ResponseBody
    @RequestMapping(value = "/index/all", method = RequestMethod.GET)
    public List<Topic> getIndexAllTopicScore() {
        return TweetService.findTopics();
    }

    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public List<Topic> getIndexPartTopicScore() {
        List<Topic> ret = new ArrayList<>();
        List<String> lst = TweetService.findTodayTopics();
        lst.forEach(x -> ret.add(TweetService.findTopic(x)));
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/index/{topicName}", method = RequestMethod.GET)
    public Topic getIndexOneTopic(@PathVariable String topicName) {
        return TweetService.findTopic(topicName);
    }

    @RequestMapping(value = "/score/{trend}", method = RequestMethod.GET)
    @ResponseBody
    public Topic getResultDependsOnScore(@PathVariable String trend) {
        return TweetService.findTopic(trend);
    }

    @RequestMapping(value = "/score", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Map.Entry<String, Integer>>> getAllResultDependsOnScore() {
        return SimpleFrequentCount.getAllResultDependsOnScore();
    }

    @RequestMapping(value = "/words/{trend}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map.Entry<String, Integer>> getTopWords(@PathVariable String trend) {
        return SimpleFrequentCount.getResult(trend);
    }

    @RequestMapping(value = "/perHour/{trend}", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getPerHourTrendCount(@PathVariable String trend) {
        return SimpleFrequentCount.getTodayPerHourTweetCount(trend);
    }
}
