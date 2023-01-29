package nl.runnable.dataset.web;

import lombok.RequiredArgsConstructor;
import nl.runnable.dataset.DatasetRepository;
import nl.runnable.dataset.DatasetSourceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/datasets")
@RequiredArgsConstructor
public class DatasetController {

    private final DatasetRepository repository;

    private final DatasetSourceFactory datasetSourceFactory;

    @GetMapping
    public ModelAndView index() {
        final var modelAndView = new ModelAndView("dataset/index.html");
        final var datasets = repository.findAll();
        modelAndView.addObject(datasetSourceFactory.createIndexPageSource(datasets));
        return modelAndView;
    }

    @GetMapping("/{name}.html")
    public ModelAndView datasetHtml(@PathVariable(value = "name") String name,
                                    @RequestParam(value = "sort", defaultValue = "1") int sort,
                                    @RequestParam(value = "asc", defaultValue = "true") boolean asc) {
        final var modelAndView = new ModelAndView();
        modelAndView.setViewName("dataset/table.html");
        final var dataset = repository.find(name).orElseThrow();
        final var source = datasetSourceFactory.createDatasetPageSource(dataset);
        modelAndView.addObject(source);
        modelAndView.addObject("sortColumn", sort);
        modelAndView.addObject("sortAscending", asc);
        return modelAndView;
    }

    @GetMapping("/{name}.xml")
    public ModelAndView datasetXml(@PathVariable(value = "name") String name) {
        final var dataset = repository.find(name).orElseThrow();
        final var source = datasetSourceFactory.createDatasetSource(dataset);

        final var modelAndView = new ModelAndView("dataset/identity.xml");
        modelAndView.addObject(source);
        return modelAndView;
    }

    @GetMapping("/{name}.csv")
    public ModelAndView datasetCsv(@PathVariable(value = "name") String name) {
        final var dataset = repository.find(name).orElseThrow();

        final var modelAndView = new ModelAndView();
        modelAndView.setView(new ResourceView(dataset.resource(), "text/plain"));
        return modelAndView;
    }

}
