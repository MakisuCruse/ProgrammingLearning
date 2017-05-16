package web.controller;

import core.Algo.SimpleFrequentCount;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;

import java.util.List;
import java.util.Map;

/**
 * Created by makisucruse on 2017/5/11.
 */
@RestController
<<<<<<< HEAD
<<<<<<< HEAD
@RequestMapping(value = "/tweet")
=======
@RequestMapping(value = "/tweet/")
>>>>>>> new
=======
@RequestMapping(value = "/tweet/")
>>>>>>> new
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

    @RequestMapping(value = "/score/{trend}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map.Entry<String, Integer>> getResultDependsOnScore(@PathVariable String trend) {
        return SimpleFrequentCount.getResultDependsOnScore(trend);
    }

    @RequestMapping(value = "/words/{trend}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map.Entry<String, Integer>> getTopWords(@PathVariable String trend) {
        return SimpleFrequentCount.getResult(trend);
    }
}
