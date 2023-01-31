package nl.runnable.dataset.web;

import lombok.RequiredArgsConstructor;
import nl.runnable.dataset.DatasetRepository;
import nl.runnable.dataset.DatasetSourceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView datasetHtml(@PathVariable(value = "name") String name) {
        return getModelAndView(name, "dataset/table.html");
    }

    @GetMapping("/{name}.pdf")
    public ModelAndView datasetPdf(@PathVariable(value = "name") String name) {
        final var dataset = repository.find(name).orElseThrow();
        final var source = datasetSourceFactory.createDatasetSource(dataset);

        final var modelAndView = new ModelAndView("dataset/table.fo");
        modelAndView.addObject(source);
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

    @ModelAttribute("sortColumn")
    public int sortColumn(@RequestParam(value = "sort", defaultValue = "1") int sort) {
        return sort;
    }

    private ModelAndView getModelAndView(String name, String viewName) {
        final var dataset = repository.find(name).orElseThrow();
        final var source = datasetSourceFactory.createDatasetPageSource(dataset);

        final var modelAndView = new ModelAndView(viewName);
        modelAndView.addObject(source);
        return modelAndView;
    }

    @ModelAttribute("sortOrder")
    public String sortOrder(@RequestParam(value = "asc", defaultValue = "true") boolean asc) {
        return asc ? "ascending" : "descending";
    }

    @ModelAttribute("pageOrientation")
    public String pageOrientation(@RequestParam(value = "portrait", defaultValue = "true") boolean portrait) {
        return portrait ? "portrait" : "landscape";
    }

}
