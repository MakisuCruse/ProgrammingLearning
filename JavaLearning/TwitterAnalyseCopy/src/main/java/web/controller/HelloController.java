package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by makisucruse on 2017/5/10.
 */
@Controller
@RequestMapping(value = "/hello")
public class HelloController {
    @RequestMapping(value = "/m", method = RequestMethod.GET)
    public String print(Model model) {
        model.addAttribute("msg", "123");
        model.addAttribute("m2", "Hello World!!");
        return "hello";
    }
}
