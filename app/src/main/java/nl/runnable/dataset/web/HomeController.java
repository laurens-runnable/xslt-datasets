package nl.runnable.dataset.web;

import lombok.RequiredArgsConstructor;
import nl.runnable.dataset.PlaceholderPageSourceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final PlaceholderPageSourceFactory placeholderPageSourceFactory;

    @GetMapping
    public ModelAndView index() throws Exception {
        final var modelAndView = new ModelAndView("page.html");
        modelAndView.addObject(placeholderPageSourceFactory.createPageSource("Home"));
        return modelAndView;
    }

}
