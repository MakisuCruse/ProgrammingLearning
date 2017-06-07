package web.controller;

import core.model.Person;
import core.service.KnowledgeBase.PersonPlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by makisucruse on 2017/5/22.
 */
@RestController
@RequestMapping(value = "/person/")
public class PersonController {
    @RequestMapping(value = "/trend", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> getPersonTrend() {
        return PersonPlaceService.getPersonTrend();
    }

    @RequestMapping(value = "one/{personName}", method = RequestMethod.GET)
    @ResponseBody
    public String getOneTrend(@PathVariable String personName) {
        return PersonPlaceService.getOneTrend(personName);
    }
}
