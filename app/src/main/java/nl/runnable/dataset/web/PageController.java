package nl.runnable.dataset.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
public class PageController {

    @GetMapping
    public ModelAndView home(@ModelAttribute Page page) {
        page.setTitle("Home");
        var mav = new ModelAndView("home");
        return mav;
    }

    @ModelAttribute
    private Page page() {
        return new Page("Dataset Visualizer");
    }

}
