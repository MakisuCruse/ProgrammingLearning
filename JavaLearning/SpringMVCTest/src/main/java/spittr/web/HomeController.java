package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by makisucruse on 16/8/25.
 */

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = GET)
    public ModelAndView home(Model model) {
        ModelAndView view = null;
        view = new ModelAndView("home");
        return view;
    }
}

